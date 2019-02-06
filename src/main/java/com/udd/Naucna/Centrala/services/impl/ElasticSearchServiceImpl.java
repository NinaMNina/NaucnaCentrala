package com.udd.Naucna.Centrala.services.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.udd.Naucna.Centrala.dto.Parametar;
import com.udd.Naucna.Centrala.dto.ParametriDTO;
import com.udd.Naucna.Centrala.dto.RadDTO;
import com.udd.Naucna.Centrala.repository.elasticSearch.ElasticSearchRepository;
import com.udd.Naucna.Centrala.services.ElasticSearchService;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
	
	@Autowired
	ElasticSearchRepository elasticSearchRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    
    @Autowired
    private Client nodeClient;
    
	@Override
	public RadDTO uploadRad(RadDTO rad) {
		File pdf = new File(rad.getFile());
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		ParseContext pcontext = new ParseContext();
		PDFParser pdfparser = new PDFParser(); 
		try {
			FileInputStream inputstream = new FileInputStream(pdf);
			try {
				pdfparser.parse(inputstream, handler, metadata,pcontext);
			//	System.out.println("Contents of the PDF :" + handler.toString());
				rad.setTekstRada(handler.toString());
			} catch (IOException | SAXException | TikaException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		rad = elasticSearchRepository.index(rad);
		RadDTO retVal = elasticSearchRepository.save(rad);
		return retVal;
	}


	@Override
	public ArrayList<RadDTO> search(ParametriDTO p) {		
		String naslov = p.getByKey("naslov");
		String autor = p.getByKey("autor");
		String kljucniPojmovi = p.getByKey("pojam");
		String tekstRada = p.getByKey("tekst");
		String naucnaOblast = p.getByKey("oblast");
		String casopis = p.getByKey("naziv");
		
		String query = "{\"query\": {\"bool\" : {\"must\" : [{\"field\" : {\"naslov\" : \""+naslov+"\"}},"
				+ "{{\"field\" : {\"autori\" : {\"query\" : \"*?*\",\""+autor+"\" : true}}} \""
						+ "{\"field\" : {\"kljucniPojmovi\" : \""+kljucniPojmovi+"\"}},"
								+ "{\"field\" : {\"tekstRada\" : \""+tekstRada+"\"}},"
										+ "{\"field\" : {\"naucnaOblast\" : \""+naucnaOblast+"\"}},"
												+ "{\"field\" : {\"casopis\" : \""+casopis+"\"}}]}}}";
		String q1 = "{\"query\": {\"bool\" : {\"must\" : [";
		String q2 = "]}, {\"should\" : [";
		String q3 = "]}}}";
		int i_and=0;
		int i_or=0;
		
		for(int i=0; i<p.getParametri().size(); i++){
			Parametar p0 = p.getParametri().get(i);
			if(p0.getOperacija().equals("AND")){
				if(i_and>0)
					q1+=", ";
				if(p0.getPolje().equals("autor")){
					q1+="{\"match\": {\"field\" : {\"autoriRada\" : {\"query\" : \"*"+p0.getVrednost()+"*\",\""+autor+"\" : true}}}}";
				}
				else{
					q1+="{\"match\": {\"field\" : {\""+p0.getPolje()+"\" : \""+p0.getVrednost()+"\"}}}";
				}
				i_and++;
			}
			if(p0.getOperacija().equals("OR")){
				if(i_or>0)
					q2+=", ";
				if(p0.getPolje().equals("autor"))
					q2+="{\"match\": {\"field\" : {\"autoriRada\" : {\"query\" : \"*"+p0.getVrednost()+"*\",\""+autor+"\" : true}}}}";
				else{
					q2+="{\"match\": {\"field\" : {\""+p0.getPolje()+"\" : \""+p0.getVrednost()+"\"}}}";
				}
				i_or++;
			}
		}
		query = q1+q2+q3;
		System.out.println(query);
		ArrayList<RadDTO> retVal = (ArrayList<RadDTO>) elasticSearchRepository.search(queryStringQuery(query));
		return retVal;
	}


	@Override
	public ArrayList<RadDTO> searchObican(String tekst) {
        ArrayList<RadDTO> retVal = new ArrayList<>();
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("tekstRada", 100)
                .highlightQuery(QueryBuilders.queryStringQuery(tekst));
        SearchRequestBuilder request = nodeClient.prepareSearch("radovi")
                .setQuery(QueryBuilders.queryStringQuery(tekst))
                .setSearchType(SearchType.DEFAULT)
                .highlighter(highlightBuilder);
        SearchResponse response = request.get();
        for(SearchHit hit : response.getHits().getHits()) {
            Gson gson = new Gson();
            RadDTO radDTO = new RadDTO();
            radDTO = gson.fromJson(hit.getSourceAsString(), RadDTO.class);
            
            String highlights = "...";

            Map<String, HighlightField> polja = hit.getHighlightFields();
            for (Map.Entry<String, HighlightField> polje : polja.entrySet()){
                String vrednost = Arrays.toString(polje.getValue().fragments());
                highlights+=vrednost.substring(1, vrednost.length()-1);
                highlights+="...";

            }

            highlights = highlights.replace("<em>", "<b>");
            highlights = highlights.replace("</em>", "</b>");
            radDTO.setTekstRada(highlights);
            retVal.add(radDTO);
        }

        return retVal;
	}
	
	

	@Override
	public ArrayList<RadDTO> moreLikeThis(Long id) {
		Optional<RadDTO> retRad = elasticSearchRepository.findById(id);
		if(!retRad.isPresent()){
			return null;
		}
		RadDTO rad = retRad.get();
		Pageable p = new Pageable();
		Page<RadDTO> result = elasticSearchRepository.searchSimilar(rad, new String[] { "naslov", "autori", "kljucniPojmovi", "naucnaOblast",  "tekstRada" }, new PageRequest(0, 10));		
		ArrayList<RadDTO> retVal = new ArrayList<>();
		if(result!=null){
			for(RadDTO r0 : result)
				retVal.add(r0);
		}
		return retVal;
	}
	
	
}

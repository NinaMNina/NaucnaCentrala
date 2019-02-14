package com.udd.Naucna.Centrala.services.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udd.Naucna.Centrala.dto.HighlightedRadDTO;
import com.udd.Naucna.Centrala.dto.ParametriDTO;
import com.udd.Naucna.Centrala.dto.RadDTO;
import com.udd.Naucna.Centrala.dto.RecenzentDTO;
import com.udd.Naucna.Centrala.model.Casopis;
import com.udd.Naucna.Centrala.model.Rad;
import com.udd.Naucna.Centrala.repository.elasticSearch.ESRecenzentRepository;
import com.udd.Naucna.Centrala.repository.elasticSearch.ElasticSearchRepository;
import com.udd.Naucna.Centrala.services.ElasticSearchService;

@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {
	
/*	@Autowired
	private ElasticSearchRepository elasticSearchRepository;
	@Autowired
	private ESRecenzentRepository esRecenzentRepository;*/
	@Override
	public RadDTO uploadRad(RadDTO rad) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<HighlightedRadDTO> searchObican(String tekst) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<RadDTO> moreLikeThis(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<HighlightedRadDTO> searchParams(ParametriDTO p) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<RecenzentDTO> findUdaljeniRecenzenti(Rad rad, Casopis casopis) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<RecenzentDTO> findSlicniRecenzenti(Rad rad, Casopis casopis) {
		// TODO Auto-generated method stub
		return null;
	}

  /*  @Autowired
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
	public ArrayList<HighlightedRadDTO> searchParams(ParametriDTO p) {
		BoolQueryBuilder query = QueryBuilders.boolQuery();			
		for(int i=0; i<p.getParametri().size(); i++){
			Parametar p0 = p.getParametri().get(i);
			if(p0.getFraza()){
				if(p0.getOperacija().equals("AND"))
					query.must(QueryBuilders.matchPhraseQuery(p0.getPolje(), p0.getVrednost()));
				else if(p0.getOperacija().equals("OR"))
					query.should(QueryBuilders.matchPhraseQuery(p0.getPolje(), p0.getVrednost()));
			}
			else{
				if(p0.getOperacija().equals("AND"))
					query.must(QueryBuilders.matchQuery(p0.getPolje(), p0.getVrednost()));
				else if(p0.getOperacija().equals("OR"))
					query.should(QueryBuilders.matchQuery(p0.getPolje(), p0.getVrednost()));
			}
		}
	//	System.out.println("QUERY:    "+query.toString());
		HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("tekstRada", 100)
                .field("casopis", 100)
                .field("naslov", 100)
                .field("autoriRada", 100)
                .field("kljucniPojmovi", 100)
                .field("naucnaOblast", 100)
                .highlightQuery(query);
		SearchRequestBuilder request = nodeClient.prepareSearch("radovi")
                .setQuery(query)
                .setSearchType(SearchType.DEFAULT)
                .highlighter(highlightBuilder);
		 SearchResponse response = request.get();
	     return processHighlights(response);
	}

	@Override
	public ArrayList<HighlightedRadDTO> searchObican(String tekst) {
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("tekstRada", 100)
                .field("casopis", 100)
                .field("naslov", 100)
                .field("autoriRada", 100)
                .field("kljucniPojmovi", 100)
                .field("naucnaOblast", 100)
                .highlightQuery(QueryBuilders.queryStringQuery(tekst));
        SearchRequestBuilder request = nodeClient.prepareSearch("radovi")
                .setQuery(QueryBuilders.queryStringQuery(tekst))
                .setSearchType(SearchType.DEFAULT)
                .highlighter(highlightBuilder);
        SearchResponse response = request.get();
        return processHighlights(response);
	}
	
	

	@Override
	public ArrayList<RadDTO> moreLikeThis(Long id) {
		Optional<RadDTO> retRad = elasticSearchRepository.findById(id);
		if(!retRad.isPresent()){
			return null;
		}
		RadDTO rad = retRad.get();
		Page<RadDTO> result = elasticSearchRepository.searchSimilar(rad, new String[] { "naslov", "autori", "kljucniPojmovi", "naucnaOblast",  "tekstRada" }, new PageRequest(0, 10));		
		ArrayList<RadDTO> retVal = new ArrayList<>();
		if(result!=null){
			for(RadDTO r0 : result)
				retVal.add(r0);
		}
		return retVal;
	}
	
	private ArrayList<HighlightedRadDTO> processHighlights(SearchResponse response){
		ArrayList<HighlightedRadDTO> retVal = new ArrayList<HighlightedRadDTO>();
		for(SearchHit hit : response.getHits().getHits()) {
            Gson gson = new Gson();
            RadDTO radDTO = new RadDTO();
            RadDTO radHighlights = new RadDTO();
            HighlightedRadDTO rad = new HighlightedRadDTO();
            radDTO = gson.fromJson(hit.getSourceAsString(), RadDTO.class);
            
            String highlights = "...";

            Map<String, HighlightField> polja = hit.getHighlightFields();
            for (Map.Entry<String, HighlightField> polje : polja.entrySet()){
            	if(polje.getKey().equals("tekstRada")){
	                String vrednost = Arrays.toString(polje.getValue().fragments());
	                vrednost = vrednost.substring(1, vrednost.length()-1);
	                vrednost = vrednost.replace("<em>", "<b>");
	                vrednost = vrednost.replace("</em>", "</b>");
	                highlights+=vrednost;
	                highlights+="...";
            	}
            	else if(polje.getKey().equals("casopis")){            		
	                String casopisH = Arrays.toString(polje.getValue().fragments());
	                casopisH=casopisH.substring(1, casopisH.length()-1);
	                casopisH = casopisH.replace("<em>", "<b>");
	                casopisH = casopisH.replace("</em>", "</b>");
	                radHighlights.setCasopis(casopisH);
            	}
            	else if(polje.getKey().equals("naslov")){            		
	                String naslovH = Arrays.toString(polje.getValue().fragments());
	                naslovH=naslovH.substring(1, naslovH.length()-1);
	                naslovH = naslovH.replace("<em>", "<b>");
	                naslovH = naslovH.replace("</em>", "</b>");
	                radHighlights.setNaslov(naslovH);
            	}
            	else if(polje.getKey().equals("autoriRada")){            		
	                String autoriH = Arrays.toString(polje.getValue().fragments());
	                autoriH=autoriH.substring(1, autoriH.length()-1);
	                autoriH = autoriH.replace("<em>", "<b>");
	                autoriH = autoriH.replace("</em>", "</b>");
	                radHighlights.setAutoriRada(autoriH);
            	}
            	else if(polje.getKey().equals("kljucniPojmovi")){            		
	                String kljucniPojmoviH = Arrays.toString(polje.getValue().fragments());
	                kljucniPojmoviH=kljucniPojmoviH.substring(1, kljucniPojmoviH.length()-1);
	                kljucniPojmoviH = kljucniPojmoviH.replace("<em>", "<b>");
	                kljucniPojmoviH = kljucniPojmoviH.replace("</em>", "</b>");
	                radHighlights.setKljucniPojmovi(kljucniPojmoviH);
            	}
            	else if(polje.getKey().equals("naucnaOblast")){            		
	                String naucnaOblastH = Arrays.toString(polje.getValue().fragments());
	                naucnaOblastH=naucnaOblastH.substring(1, naucnaOblastH.length()-1);
	                naucnaOblastH = naucnaOblastH.replace("<em>", "<b>");
	                naucnaOblastH = naucnaOblastH.replace("</em>", "</b>");
	                radHighlights.setNaucnaOblast(naucnaOblastH);
            	}
            }
            highlights+="...";
            if(highlights.equals("......")){
            	radHighlights.setTekstRada(radDTO.getTekstRada().substring(0, 400)+"...");
            }
            else
                radHighlights.setTekstRada(highlights);
            radDTO.setTekstRada("");
            rad.setHighlights(radHighlights);
            rad.setRad(radDTO);
            retVal.add(rad);
        }

        return retVal;
	}


	@Override
	public ArrayList<RecenzentDTO> findUdaljeniRecenzenti(Rad rad, Casopis casopis) {
		RadDTO rdto = elasticSearchRepository.findByNaslov(rad.getNaslov());
		BoolQueryBuilder query = QueryBuilders.boolQuery();	
		query.must(QueryBuilders.matchPhraseQuery("casopis", casopis.getNaziv()));		
		
		GeoDistanceQueryBuilder geoDistanceFilterBuilder = new GeoDistanceQueryBuilder("location");
				geoDistanceFilterBuilder.point(rad.getOdgovorniAutor().getLokacija().getX(), rad.getOdgovorniAutor().getLokacija().getY())
			      .distance(100, DistanceUnit.KILOMETERS)
			      .geoDistance(GeoDistance.ARC);
		query.mustNot(geoDistanceFilterBuilder);
		SearchRequestBuilder searchRequestBuilder = nodeClient.prepareSearch("recenzenti").setTypes("recenzenti")
			    .setQuery(query)
			    .setFrom(0);
		SearchResponse resp = searchRequestBuilder.execute().actionGet();
		ArrayList<RecenzentDTO> retVal = new ArrayList<RecenzentDTO>();
		for (SearchHit hit : resp.getHits()) {
			Gson gson = new Gson();
            RecenzentDTO recenzentDTO = new RecenzentDTO();
            recenzentDTO = gson.fromJson(hit.getSourceAsString(), RecenzentDTO.class); 
            retVal.add(recenzentDTO);
		}
		return retVal;

	}


	@Override
	public ArrayList<RecenzentDTO> findSlicniRecenzenti(Rad rad, Casopis casopis) {
		RadDTO rdto = elasticSearchRepository.findByNaslov(rad.getNaslov());
		BoolQueryBuilder query = QueryBuilders.boolQuery();	
		query.must(QueryBuilders.matchPhraseQuery("casopis", casopis.getNaziv()));	
		ArrayList<RecenzentDTO> retVal = new ArrayList<RecenzentDTO>();
		MoreLikeThisQueryBuilder mlt;
		String[] field = {"tekstovi"};
		String[] value = {getTekstZaSlican(rad.getLokacijaProbnogRada())};
		mlt = new MoreLikeThisQueryBuilder(field, value, null)
				.analyzer("serbian-analyzer")
				.minimumShouldMatch("70%")
				.minTermFreq(20);
		query.must(mlt);
		SearchRequestBuilder searchRequestBuilder = nodeClient.prepareSearch("recenzenti").setTypes("recenzenti")
			    .setQuery(query)
			    .setFrom(0);
		SearchResponse resp = searchRequestBuilder.execute().actionGet();
		for (SearchHit hit : resp.getHits()) {
			Gson gson = new Gson();
            RecenzentDTO recenzentDTO = new RecenzentDTO();
            recenzentDTO = gson.fromJson(hit.getSourceAsString(), RecenzentDTO.class); 
            retVal.add(recenzentDTO);
		}
		
		return retVal;
	}


	private String getTekstZaSlican(String lokacijaProbnogRada) {
		String retVal = "";
		File pdf = new File(lokacijaProbnogRada);
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		ParseContext pcontext = new ParseContext();
		PDFParser pdfparser = new PDFParser(); 
		try {
			FileInputStream inputstream = new FileInputStream(pdf);
			try {
				pdfparser.parse(inputstream, handler, metadata,pcontext);
			//	System.out.println("Contents of the PDF :" + handler.toString());
				retVal = handler.toString();
			} catch (IOException | SAXException | TikaException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return retVal;
	}


*/
	
	
}

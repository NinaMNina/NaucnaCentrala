package com.udd.Naucna.Centrala;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import com.udd.Naucna.Centrala.model.Autor;
import com.udd.Naucna.Centrala.model.Casopis;
import com.udd.Naucna.Centrala.model.Izdanje;
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.model.Kupljeno;
import com.udd.Naucna.Centrala.model.NaucnaOblast;
import com.udd.Naucna.Centrala.model.PretplataNaCasopis;
import com.udd.Naucna.Centrala.model.Rad;
import com.udd.Naucna.Centrala.model.Recenzent;
import com.udd.Naucna.Centrala.model.Urednik;
import com.udd.Naucna.Centrala.model.UrednikNO;
import com.udd.Naucna.Centrala.model.enums.StatusRada;
import com.udd.Naucna.Centrala.repository.AutorRepository;
import com.udd.Naucna.Centrala.repository.CasopisRepository;
import com.udd.Naucna.Centrala.repository.IzdanjeRepository;
import com.udd.Naucna.Centrala.repository.NaucnaOblastRepository;
import com.udd.Naucna.Centrala.repository.RadRepository;
import com.udd.Naucna.Centrala.repository.RecenzentRepository;
import com.udd.Naucna.Centrala.repository.UrednikNORepository;
import com.udd.Naucna.Centrala.repository.UrednikRepository;
import com.udd.Naucna.Centrala.repository.elasticSearch.ESRecenzentRepository;

@Component
public class StartData {
	@Autowired
    private AutorRepository autorRepository;
	@Autowired
    private CasopisRepository casopisRepository;
	@Autowired
    private UrednikRepository urednikRepository;
	@Autowired
    private IzdanjeRepository izdanjeRepository;
	@Autowired
    private NaucnaOblastRepository naucnaOblastRepository;
	@Autowired
    private RadRepository radRepository;
	@Autowired
    private RecenzentRepository recenzentRepository;
	@Autowired
    private UrednikNORepository urednikNORepository;
//	@Autowired
//    private ESRecenzentRepository esRecenzentRepository;
	@Autowired
    private IdentityService identityService;
	
	@PostConstruct
    private void init(){
//AUTORI
		Autor autor0 = new Autor(null, "nina", "nina", "Nina", "Miladinovic", "ninamiladinovic@hotmail.com", new Point(new Double(45.25167), new Double(19.83694)), new ArrayList<>(),new ArrayList<>());
		autor0 = autorRepository.save(autor0);
		Autor autorrad1MU = new Autor(null, "radovan", "radovan", "Radovan", "Turović", "radovan.turovic@uns.ac.rs", new Point(new Double(45.25167), new Double(19.83694)), new ArrayList<>(),new ArrayList<>());
		autorrad1MU = autorRepository.save(autorrad1MU);		
		Autor autorrad2MU = new Autor(null, "aleksandra", "aleksandra", "Aleksandra", "Mitrović", "aleksandramitrovic@uns.ac.rs", new Point(new Double(43.84864), new Double(18.35644)), new ArrayList<>(),new ArrayList<>());
		autorrad2MU = autorRepository.save(autorrad2MU);
		Autor autorrad3MU = new Autor(null, "nemanja", "nemanja", "Nemanja", "Miladinović", "nemanja.miladinovic@live.com", new Point(new Double(45.25167), new Double(19.83694)), new ArrayList<>(),new ArrayList<>());
		autorrad3MU = autorRepository.save(autorrad3MU);
		Autor autorrad1NG = new Autor(null, "lazar", "lazar", "Lazar", "Milin", "lazar.milin@live.com", new Point(new Double(45.25167), new Double(19.83694)), new ArrayList<>(),new ArrayList<>());
		autorrad1NG = autorRepository.save(autorrad1NG);//Novi Sad
		Autor autorrad2NG = new Autor(null, "dragutin", "dragutin", "Dragutin", "Mataruga", "dragutin.mataruga@live.com", new Point(new Double(44.787197), new Double(20.457273)), new ArrayList<>(),new ArrayList<>());
		autorrad2NG = autorRepository.save(autorrad2NG);
		Autor autorrad3NG = new Autor(null, "tijana", "tijana", "Tijana", "Ivanović", "tijana.ivanovic@live.com", new Point(new Double(43.32472), new Double(21.90333)), new ArrayList<>(),new ArrayList<>());
		autorrad3NG = autorRepository.save(autorrad3NG);
		
		Group autori = identityService.newGroup("autor");
		autori.setName("autori");
		autori.setType("AUTORI");
	    identityService.saveGroup(autori);
	    
		User nina = identityService.newUser(autor0.getKorisnickoIme());
		nina.setFirstName(autor0.getIme());
		nina.setLastName(autor0.getPrezime());
		nina.setEmail(autor0.getEmail());
		nina.setPassword(autor0.getLozinka());
		identityService.saveUser(nina);	
		identityService.createMembership("nina", "autor");
		
		User radovan = identityService.newUser(autorrad1MU.getKorisnickoIme());
		radovan.setFirstName(autorrad1MU.getIme());
		radovan.setLastName(autorrad1MU.getPrezime());
		radovan.setEmail(autorrad1MU.getEmail());
		radovan.setPassword(autorrad1MU.getLozinka());
		identityService.saveUser(radovan);	
		identityService.createMembership("radovan", "autor");
		
		Group urednici = identityService.newGroup("urednik");
		identityService.saveGroup(urednici);
		Group recenzenti = identityService.newGroup("recenzent");
		identityService.saveGroup(recenzenti);
		Group uredniciNO = identityService.newGroup("urednikNO");
		identityService.saveGroup(uredniciNO);

		
		
//CASOPISI
		Casopis casNG = new Casopis(null, "Nacionalna Geografija", true, "12345678", new ArrayList<NaucnaOblast>(), null, new ArrayList<UrednikNO>(),
				new ArrayList<Recenzent>(), new ArrayList<Izdanje>());
		casNG = casopisRepository.save(casNG);
		Casopis casMU = new Casopis(null, "Mašinsko Učenje 101", false, "87654321", new ArrayList<NaucnaOblast>(), null, new ArrayList<UrednikNO>(),
				new ArrayList<Recenzent>(), new ArrayList<Izdanje>());
		casMU = casopisRepository.save(casMU);
//IZDANJA
		String string4 = "February 1, 2019";
		String string5 = "March 1, 2019";
		String string6 = "April 1, 2019";
		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		Date date4 = new Date();
		Date date5 = new Date();
		Date date6 = new Date();
		try {
			date4 = format.parse(string4);
			date5 = format.parse(string5);
			date6 = format.parse(string6);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Izdanje izd1NG = new Izdanje(null, casNG, date4, date5, new ArrayList<Rad>());	
		Izdanje izd2NG = new Izdanje(null, casNG, date5, date6, new ArrayList<Rad>());
		izd1NG = izdanjeRepository.save(izd1NG);
		izd2NG = izdanjeRepository.save(izd2NG);
		ArrayList<Izdanje> izdanjaNG = new ArrayList<Izdanje>();
		izdanjaNG.add(izd1NG);
		izdanjaNG.add(izd2NG);
		casNG.setIzdanja(izdanjaNG);
		casNG = casopisRepository.save(casNG);
		
		
		Izdanje izd1MU = new Izdanje(null, casMU, date4, date5, new ArrayList<Rad>());	
		Izdanje izd2MU = new Izdanje(null, casMU, date5, date6, new ArrayList<Rad>());
		izd1MU = izdanjeRepository.save(izd1MU);
		izd2MU = izdanjeRepository.save(izd2MU);
		ArrayList<Izdanje> izdanjaMU = new ArrayList<Izdanje>();
		izdanjaMU.add(izd1MU);
		izdanjaMU.add(izd2MU);
		casMU.setIzdanja(izdanjaMU);
		casMU = casopisRepository.save(casMU);	
//NAUCNA OBLAST
		NaucnaOblast naucnaOblast0 = new NaucnaOblast(null, "Inženjerstvo i Tehnologije", 2, "Ostalo", 11, "");
		naucnaOblast0 = naucnaOblastRepository.save(naucnaOblast0);
		NaucnaOblast naucnaOblast1 = new NaucnaOblast(null, "Prirodne Nauke", 1, "Biologija", 6, "");
		naucnaOblast1 = naucnaOblastRepository.save(naucnaOblast1);
		NaucnaOblast naucnaOblast2 = new NaucnaOblast(null, "Medicina i Zdravlje", 3, "Ostalo", 5, "");
		naucnaOblast2 = naucnaOblastRepository.save(naucnaOblast2);
		NaucnaOblast naucnaOblast3 = new NaucnaOblast(null, "Prirodne Nauke", 1, "Kompjuteri i Informacione Nauke", 2, "");
		naucnaOblast3 = naucnaOblastRepository.save(naucnaOblast3);
		
		ArrayList<NaucnaOblast> nOblastNG = new ArrayList<NaucnaOblast>();
		nOblastNG.add(naucnaOblast1);
		nOblastNG.add(naucnaOblast2);
		casNG.setNaucneOblasti(nOblastNG);
		casNG = casopisRepository.save(casNG);
		
		ArrayList<NaucnaOblast> nOblastMU = new ArrayList<NaucnaOblast>();
		nOblastMU.add(naucnaOblast0);
		nOblastMU.add(naucnaOblast3);
		casMU.setNaucneOblasti(nOblastMU);
		casMU = casopisRepository.save(casMU);
		ArrayList<NaucnaOblast> nOblast3 = new ArrayList<NaucnaOblast>();
		nOblast3.add(naucnaOblast0);
		nOblast3.add(naucnaOblast2);
		ArrayList<NaucnaOblast> nOblast4 = new ArrayList<NaucnaOblast>();
		nOblast4.add(naucnaOblast0);
		nOblast4.add(naucnaOblast1);
		ArrayList<NaucnaOblast> nOblast5 = new ArrayList<NaucnaOblast>();
		nOblast5.add(naucnaOblast1);
		nOblast5.add(naucnaOblast3);
		ArrayList<NaucnaOblast> nOblast6 = new ArrayList<NaucnaOblast>();
		nOblast6.add(naucnaOblast1);
		nOblast6.add(naucnaOblast3);
		nOblast6.add(naucnaOblast2);

//RECENZENTI
		ArrayList<Casopis> cas1 = new ArrayList<Casopis>();
		cas1.add(casNG);
		ArrayList<Casopis> cas2 = new ArrayList<Casopis>();
		cas2.add(casMU);
		ArrayList<Casopis> casOba = new ArrayList<Casopis>();
		casOba.add(casNG);
		casOba.add(casMU);
		Recenzent rec1 = new Recenzent(null, "rec1", "rec1", "Pera", "Perić", "pera.p@live.com", new Point(new Double(45.25167), new Double(19.83694)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), cas1, "mr", nOblastNG);
		rec1 = recenzentRepository.save(rec1);//Novi Sad
		Recenzent rec2 = new Recenzent(null, "rec2", "rec2", "Mika", "Mikić", "mika.m@live.com", new Point(new Double(43.32472), new Double(21.90333)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), cas2, "ms", nOblastMU);
		rec2 = recenzentRepository.save(rec2);//Nis		
		Recenzent rec3 = new Recenzent(null, "rec3", "rec3", "Jose", "Muchaco", "ho.mu@live.com", new Point(new Double(40.4165), new Double(-3.70256)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), casOba, "dr", nOblast3);
		rec3 = recenzentRepository.save(rec3);//Madrid
		Recenzent rec4 = new Recenzent(null, "rec4", "rec4", "Koja", "Kojić", "koja.kojic@live.com", new Point(new Double(46.05108), new Double(14.50513)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), casOba, "dr", nOblast4);
		rec4 = recenzentRepository.save(rec4);//Ljubljana			
		Recenzent rec5 = new Recenzent(null, "rec5", "rec5", "Mujo", "Mujić", "m.mujic@live.com", new Point(new Double(44.53842), new Double(18.66709)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), casOba, "ms", nOblast5);
		rec5 = recenzentRepository.save(rec5);//Tuzla			
		Recenzent rec6 = new Recenzent(null, "rec6", "rec6", "Ištvan", "Sabo", "isabo@live.com", new Point(new Double(47.49801), new Double(19.03991)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), cas1, "dr", nOblast6);
		rec6 = recenzentRepository.save(rec6);//Budimpesta	
		Recenzent rec7 = new Recenzent(null, "rec7", "rec7", "Todor", "ilić", "ilic.t@live.com", new Point(new Double(45.25167), new Double(19.83694)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), cas1, "mr", nOblastNG);
		rec7 = recenzentRepository.save(rec7);//Novi Sad
//camunda		
		User rec1C = identityService.newUser(rec1.getKorisnickoIme());
		rec1C.setFirstName(rec1.getIme());
		rec1C.setLastName(rec1.getPrezime());
		rec1C.setEmail(rec1.getEmail());
		rec1C.setPassword(rec1.getLozinka());
		identityService.saveUser(rec1C);			
		identityService.createMembership(rec1.getKorisnickoIme(), "recenzent");
		
		User rec2C = identityService.newUser(rec2.getKorisnickoIme());
		rec2C.setFirstName(rec2.getIme());
		rec2C.setLastName(rec2.getPrezime());
		rec2C.setEmail(rec2.getEmail());
		rec2C.setPassword(rec2.getLozinka());
		identityService.saveUser(rec2C);			
		identityService.createMembership(rec2.getKorisnickoIme(), "recenzent");
		
		User rec3C = identityService.newUser(rec3.getKorisnickoIme());
		rec3C.setFirstName(rec3.getIme());
		rec3C.setLastName(rec3.getPrezime());
		rec3C.setEmail(rec3.getEmail());
		rec3C.setPassword(rec3.getLozinka());
		identityService.saveUser(rec3C);			
		identityService.createMembership(rec3.getKorisnickoIme(), "recenzent");
		
		User rec4C = identityService.newUser(rec4.getKorisnickoIme());
		rec4C.setFirstName(rec4.getIme());
		rec4C.setLastName(rec4.getPrezime());
		rec4C.setEmail(rec4.getEmail());
		rec4C.setPassword(rec4.getLozinka());
		identityService.saveUser(rec4C);			
		identityService.createMembership(rec4.getKorisnickoIme(), "recenzent");
		
		User rec5C = identityService.newUser(rec5.getKorisnickoIme());
		rec5C.setFirstName(rec5.getIme());
		rec5C.setLastName(rec5.getPrezime());
		rec5C.setEmail(rec5.getEmail());
		rec5C.setPassword(rec5.getLozinka());
		identityService.saveUser(rec5C);			
		identityService.createMembership(rec5.getKorisnickoIme(), "recenzent");
		
		User rec6C = identityService.newUser(rec6.getKorisnickoIme());
		rec6C.setFirstName(rec6.getIme());
		rec6C.setLastName(rec6.getPrezime());
		rec6C.setEmail(rec6.getEmail());
		rec6C.setPassword(rec6.getLozinka());
		identityService.saveUser(rec6C);			
		identityService.createMembership(rec6.getKorisnickoIme(), "recenzent");

		User rec7C = identityService.newUser(rec7.getKorisnickoIme());
		rec7C.setFirstName(rec7.getIme());
		rec7C.setLastName(rec7.getPrezime());
		rec7C.setEmail(rec7.getEmail());
		rec7C.setPassword(rec7.getLozinka());
		identityService.saveUser(rec7C);			
		identityService.createMembership(rec7.getKorisnickoIme(), "recenzent");
		
/*		String tekst1 = "Najpoznatiji tip kretanja životinja vezan je za kontrahovanje i opuštanje mišićnih ćelija.Funkcija mišića zasniva se na djelatnosti kontraktibilnih ćelija koje su obično grupisane i tvore mišićno tkivo. One su sposobne za aktivno skraćivanje, dok se izduživanje odvija kao pasivan odgovor na spoljašnju silu, što može na primjer da bude antagonistički mišić ili hidrostatički pritisak. Mišićno tkivo obezbjeđuje kretanje organizma, ali isto tako i cirkulaciju tjelesnih tečnosti, propulziju hrane kroz digestivni trakt, veličinu lumena arterijskih sudova, te punjenje i pražnjenje mokraćnog i žučnog mjehura. Povezani sa čvrstim skeletnim elementima mišići mogu da ostvare vrlo kompleksne pokrete. Skeletni mišići kičmenjaka su dobro proučeni, dobrim dijelom zbog svoje velič ine i pogodnosti za eksperimenalni rad.Skeletni mišići čine veliki dio organizma kič menjaka. U životinja mišićna masa čini oko 40 % ukupne mase tijela.Njihova funkcija leži u brzom odgovoru na stimulus i pod kontrolom je nervnog sistema.Oni obično funkcionišu kao dio sistema poluga sačinjenih od skeletnih elemenata, za koje su prič vršćeni č vrstim vezivnim tkivom (tetivama). Često se mišići pojavljuju kao antagonistički par - kontrakcija jednog izaziva pokret u jednom, a kontrakcija drugog u suprotnom pravcu. Ponekad kontrakcija jednog od njih refleksno inhibira kontrakciju drugog. Skeletni mišići su obavijeni vezivnim omotač em, epimizijumom,od koga polaze septe (perimizijum) u mišić gdje omotavaju snopove mišićnih vlakana (fascikule). Perimizijum čini jedinstvo sa trakama vezivnog tkiva (endomizijumom) koje se nalaze oko svakog mišićnog vlakna. Nabrojani dijelovi vezivnog tkiva se na završetku mišića spajaju sa tetivom koja je na svom drugom kraju vezana za koštani element. Anatomska i fiziološka osnova kretanja životinja Postoje tri nivoa CNS koji kontrolišu somatsku motornu aktivnost, a organizovani su hijerarhijski i paralelno (Sl. 1). Kičmena moždina sadrži neuronske mreže koje mogu da kontrolišu različite automatske stereotipne reflekse, koji mogu da funkcionišu i kada je kičmena moždina odvojena od viših delova CNS. Moždano stablo sadrži više eferentnih puteva koji učestvuju u kontroli aktivnosti kičmene moždine. Moždano stablo integriše po milion aksona sa svake strane. U produženoj moždini vlakna se grupišu i formiraju piramide na ventralnoj strani produžene moždine. Na granici produžene i kičmene moždine . vlakana se ukršta i formira lateralni kortikospinalni trakt, a neukrštena vlakna formiraju ventralni kortikospinalni trakt koji je filogenetski stariji. Kortikobulbarna vlakna završavaju se direktno na motoneuronima koji inervišu mišiće glave i lica. Aksoni ventralnog trakta završavaju se na interneuronima koji kontrolišu motoneurone aksijalnih mišića trupa i vrata i proksimalnih mišića udova. Aksoni lateralnog trakta završavaju se direktno na motoneuronima koji inervišudistalne mišiće ekstremiteta. Pre sinapse sa motoneuronima se ukrštaju.";
		String tekst2 = "Korišćenje životinja u naučnim istraživanjima, različitim testiranjima ili u nastavi predstavlja oblast aktivne diskusije i podeljenih mišljenja ne samo među laicima već i u stručnoj javnosti. Sa jedne strane su oni koji smatraju da je upotreba životinja još uvek neophodna i nužna u sticanju naučnih saznanja i da je uz poštovanje određenih etičkih principa opravdano žrtvovati životinje zarad benefita ljudi. Sa druge strane jednako prisutna su i mišljenja koja ne samo da postavljaju pitanje održive etičke opravdanosti upotrebe životinja već i potenciraju nedovoljnu pouzdanost ovako dobijenih rezultata kada se oni primenjuju na ljude. Istraživanja su pokazala da se veliki broj rezultata dobijenih u eksperimentima pokazao neprimenljiv na ljude zbog specifičnosti vrsta. Svaki trenutak života oglednih životinja ispunjen je stresom, strahom, bolom i patnjom u većoj ili manjoj meri u zavisnosti od vrste i trajanja eksperimenta. Uslovi života su daleko ispod minimuma potrebnog za zadovoljenje osnovnih potreba što je dovoljno da izazove stres i strah. Eksperimenti su po definiciji agresivni i razarajući što život oglednih životinja čini bolnim i ispunjenim patnjom, pa je smrt kojom se svaki eksperiment završava – olakšanje. Iz ovih razloga naše udruženje se zalaže za potpuni prestanak korišćenja životinja u eksperimentima. Eksperimentalna praksa koja uključuje životinje ima dugačku istoriju i predistoriju. Verujemo da će vremenom naučnici usavršiti neku drugu alternativnu istraživačku metodologiju koja će zameniti upotrebu živih životinja. Činjenica je da je i pored postojećih alternativa i dalje ubedljivo dominantan animalni model u nauci. Evo kako je počelo ... Može se reći da je ozbiljnija disekcija i vivisekcija životinja u Evropi počela još u 12. veku. Mada su prva eksperimentisanja na životinjama zabeležena i mnogo ranije, u 3. i 2. veku p.n.e. u Antičkoj Grčkoj, Galen (Γαληνός) vrši ispitivanja na svinjama i kozama sa ciljem praćenja efekata presecanja raznih nerava. Ekspanzija istraživanja na životinjama zapravo počinje u 16. veku kada su stvoreni određeni preduslovi čemu su posebno doprineli stav zvanične crkve koja se protivila disekciji ljudskih leševa, kao i rastuća potreba za saznanjima iz fiziologije i anatomije. Takođe opravdanost upotrebe životinja u cilju sticanja naučnih znanja ubrzo dobija potporu u kartezijanskoj racionalističkoj filozofiji koja na životinje gleda kao na automate, mašine čiji organizam funkcioniše po principu mehanizma bilo koje nežive stvari. Kako su naučnici poput Andreasa Vazeliusa (Andreas Vesalius), Frensisa Bekona (Francis Bacon), Renea Dekarta (René Descartes), Entona van Levenhuka (Anton van Leeuwnhoek), na životinjama isprobavali svoje hipoteze, javlja se i kritika opravdanosti upotrebe životinja u tadašnjoj istraživačkoj praksi. Ne zaboravimo da su pioniri eksperimentisanja na životinjama ističući fiziološku i anatomsku sličnost ljudi i životinja, smatrali da životinje ne samo što nemaju dušu i razum nego i nisu sposobne za bol i patnju kao čovek, te njihovi krici nisu ništa manje različiti od zvuka koje proizvode različite mehaničke naprave. Vođeni ovakvim stavom oni su izvodili veoma bolne i okrutne eksperimente.Engleski filozof iz 18. veka Džeremi Bentam (Jeremy Bentham), začetnik utilitarizma je doveo u pitanje navedeno dominantno mišljenje i ozbiljnim stavovima stavio do znanja nužnost pronalaženja kompromisa između benefita koje dobija čovek i cene koju plaćaju životinje. Utilitaristički pristup se i danas koristi kao osnovni princip u radu i odlučivanju etičkih komisija za zaštitu oglednih životinja. Ovaj pristup je takođe i osnova kreiranja tzv. „3R pravila“. Nadalje u 19. veku stvara se već jasna polarizacija kada su u pitanju stavovi prema korišćenju životinja u nauci. Sa jedne strane su Fransoa Mažendi (François Magendie) i Klod Bernar (Claude Bernard) koji se smatraju začetnicima moderne eksperimentalne fiziologije. Sa druge strane antivivisekcionisti, posebno u Engleskoj, su bili veoma aktivni. Tako je organizovan protest 1874. godine kada je predsednik Kraljevskog koledža za hirurge simbolično oslobodio jednog psa korišćenog u ogledima. Svi ovi napori ozvaničeni su donošenjem prvog britanskog zakona o zaštiti životinja 1876. godine. Takođe, Britanska asocijacija za unapređenje nauke je par godina pre toga, 1871., izdala „Akt o pravilima izvođenja fizioloških ogleda na životinjama“. Sve to ukazuje na rastuću svest o potrebama životinja koje su slične potrebama ljudi, a odnose se pre svega na sposobnost životinja da dožive bol, patnju, strah i stres.";
		String tekst3 = "Zemljište (ili pedološki pokrivаč) je rаstresiti površinski sloj zemljine kore koji se, zа rаzliku od mаsivne stene, odlikuje plodnošću. Ovаj površinski sloj zemljine kore izmenjen je i stаlno se menjа pod uticаjem аtmosferskih i bioloških fаktor (nаročito temperаture, vode, vаzdušnih pokretа i zemljine teže). Od živih orgаnizаmа, u procesustvаrаnjа zemljištа nаročito su znаčаjni biljni orgаnizmi, аli su pri tome znаčаjne i životinje. Ostаci uginulih orgаnizаmа u rаzličitim fаzаmа rаzgrаdnje i minerаlizаcije ulаze u sаstаv zemljištа. Nа tаj nаčin zemljište predstаvljа specifičаn kompleks ekoloških fаktorа (edаfski fаktori). Zemljišni prostor, а posebno njegov produktivni deo ugrožen je ljudskom аktivnošću. Ovo se nаročito odnosi nа poljoprivredno zemljište koje postаje sve ugroženije. Nаjčešće je to orgаnizаcijа, industrijаlizаcijа, površinski kopovi ugljа, putevi i drugo. Rаčunа se dа Srbijа nа ovаj nаčin gubi svаke godine oko 4000 plodnog zemljištа. Ugržаvаnje zemljištа izrаženo je potencirаnjem procesа erozije, odnosno odnošenje zemljištа vodom i vetrom (eolskа erozijа). Erozijа zemljištа, kаo posledicа uništаvаnjа šumskog pokrivаčа i uopšte vegetаcije, ozbiljаn je problem u mnogim zemljаmа. Rаzlikujemo vodenu i eolsku eroziju. U prvom slučаju vodа, otičući preko površine podloge, odnosi zemljište sve dаlje i dаlje, vodotokovimа, sve do okeаnа u kojimа se rаsipа i bivа skoro nepovrаtno izgubljenа zа kopnene ekosisteme. NJihovim dugotrаjnim delovаnjem moguće je dа se čitаvo zemljište odnese sа ugrožene površine i dа se teren ogoli sve do kаmene podloge. U slučаju erolske erozije vetаr je tаj koji odnosi zemljište sа površine terenа i rаzvejаvа gа nа sve strаne. U Srbiji je dаnаs procesom erozije ugroženo oko 3 500 000 hektаrа. Sve veće korišćenje hemijskih sredstаvа u poljoprivredi (intezivno korišćenje minerаlnih đubrivа, rаznih sredstаvа zа zаštitu biljа odnosno pesticidа, deponovаnje čvrstih otpаdаkа itd) dovodi do znаtnog zаgаđivаnjа poljoprivrednog zemljištа. Zemljište, tаkođe ugrožаvаju i zаgаđuju rаzni mаterijаli koji dospevаju vodom i vаzduhom. Jedаn od znаčаjnih fаktorа ugrožаvаnjа, predstаvljаju i otpаdne vode. Vаzduhom dospevаju rаzličite mаterije. Kаo posledicа, nаjpre strаdа vegetаcijа а potom i sаmo zemljište. Ivim putem u zemljište dospevаju i znаtne količine rаdionuklidа. U zаgаđenа zemljištа se ubrаjаju i površine rаznih rudnikа u kojimа se površinskim ili drugim putem vаdi rudа, površine pod fаbrikаmа, postrojenjimа, nаseljimа itd. Mašinsko učenje se zasniva na ideji da postoje generički algoritmi koji vam mogu reći nešto interesantno o skupu podataka, a da pritom vi ne morate da napišete poseban kod za taj problem. Umesto da pišete kod, vi ubacite podatke u generički algoritam, a on napravi svoju logiku na osnovu podataka.Na primer, jedna vrsta ovakvih algoritama je klasifikacioni algoritam. On može da smesti podatke u različite grupe. Isti klasifikacioni algoritam koji se koristi da prepozna rukom napisane brojeve mogao bi da se koristi za klasifikaciju mejlova u “spam” i “nije spam”, bez promene i jedne jedine linije koda. To je isti algoritam, ali su u njega uneti različiti trening podaci, pa on smišlja različitu logiku za  klasifikaciju.";
		String tekst4 = "Jedan od najsvežijih trendova u svetu mašinskog učenja obećava moćne rezultate i dovoljno generičke modele, namenjene još jednom koraku u evoluciji sveta veštačke inteligencije. Mehanizmi dubokog učenja su namenjeni modelovanju apstrakcija visokog nivoa u podacima pomoću matematičkih metoda obrade, sastavljenih iz linearnih i nelinearnih transformacija. Motivacija iza dubokog učenja dolazi iz intuicije, rezultata empirijskih istraživanja te istraživanja iz akademskih struka neuronauka. Duboko učenje koristi modele neuronskih mreža koje su sastavljene iz velikog broja slojeva (često i preko stotinu), često složenih i u trodimenzionalne modele. Posebnu popularnost su ovi modeli stekli pre četiri godine kada su uspeli da u performansama pobede tradicionalne pristupe koji su u to vreme smatrani vrhuncem tehnologije, poput onih korišćenih za klasifikaciju slika ili prepoznavanje govora. Popularnost ovih metoda posebno je pojačana radom kompanija kao što su Google i Facebook, čija su skladišta podataka rasla enormnom brzinom, pa je bilo potrebno iznedriti inovativne modele pomoću kojih bi ta količina i rezolucija podataka mogla pravilno da se iskoristi. Jednostavnim rečnikom, mehanizmi dubokog učenja su zasnovani na neuronskim mrežama koje odlikuje veći broj skrivenih slojeva, povezanih na inovativne načine. Posebni doprinos ovoj oblasti je napravljen od strane kompanije Google, koja je alat razvijen u svojim laboratorijama pustila u svet otvorenog kôda. Ovaj projekat po imenu Tensorflow svakog dana dobija nove korisnike i širi spektar upotrebe. Iza Tensorflow projekta stoje izuzetna imena. Prvo ime koje vam upada u oči jeste Jeff Dean (poznat kao „otac” programskog modela MapReduce), zatim Ian Goodfellow (među vodećim ljudima projekta Theano/PyLearn2) i Yangqing Jia (vodeći čovek radnog okvira Caffe). Radi se, dakle, o vodećim imenima industrije kada je u pitanju segment mašinskog učenja. Kada na sve to dodate poslovično izvrsnu organizaciju i infrastrukturu koju Google pruža, jasno je da se radi o projektu koji je tu da ostane i postane prvi radni okvir za mašinsko učenje. Tensorflow dosta svojih osnovnih elemenata duguje Theano projektu. Neke probleme ovog projekta nije nasledio, ali dolazi sa svojim nedoslednostima i nesavršenostima. Često se nalazi u vrhu testova modela za mašinsko učenje, a oni koji imaju više iskustva preporučuju da se obavezno, pored pomenutih Tensorflow i Theano radnih okvira, pogleda i projekat Torch koji ima veću fleksibilnost od dva pomenuta, jer se radi o imperativnom, a ne deklarativnom modelu. Kod drugopomenutih je neophodno definisati (deklarisati) graf izračunavanja, što određene operacije otežava. Ipak, korišćenje Torcha će zahtevati da naučite i Lua programski jezik, koji još uvek nema ni popularnost ni podršku koja bi vas na svoju stranu brzo prebacila. Interesantno je reći da Tensorflow ima dosta dobro rešen interfejs u vidu podrške za Python ali i C++, što nije čest slučaj u zajednici. Ovo dosta olakšava upotrebu, jer pomoću interfejsa za C++ Tensorflow može da se koristi u veoma zanimljivim primenama na visokom nivou. Zahvaljujući ovom interfejsu, Tensorflow projekti se mogu kompajlirati za ARM uređaje, što proširuje porodicu uređaja koje je moguće koristiti. Još kada bi svemu ovome dodali interfejs za TypeScript... gde bi im kraj bio. To, i činjenica da projekti još uvek ne mogu da se koriste na Windows operativnom sistemu. Ima li to veze sa odnosom kompanije Google sa tvorcem Windowsa ili pak nečim drugim, ostavljamo vam da sami zaključite";
		String tekst5 = "Mašinsko učenje je velika i veoma važna oblast. Nikako je ne možemo nazvati mladom, mada su ozbiljniji i javnosti opipljiviji rezultati mašinskog učenja postali značajni tek u poslednjih nekoliko godina, kada su koncepti velike količine podataka (eng. Big Data) dobili na značaju. Način na koji danas Google funkcioniše, kako u oblasti serviranja rezultata pretraga koje korisnici sprovode tako i u konktestu noviteta koje isporučuje, poput samovozećeg automobila, zasnovan je na obilatoj upotrebi mašinskog učenja. Amazon i Netflix? Njihova sposobnost da vam ispravno serviraju predloge za novu kupovinu ili gledanje novih filmova ili serija zasnovano je upravo na modelima mašinskog učenja. Od skora, mašinsko učenje je pronašlo svoje mesto i u bankarskim softverima velikih finansijskih institucija, koje su verovatno najtradicionalnije u svetu tehnologija. U ovom tekstu smo samo zagrebali površinu mašinskog učenja i srodnih disciplina. Internet obiluje razradama osnovnih koncepata koje smo prikazali. Želja je autora ovog teksta da ukaže na važnost ove discipline i na njenu vrednost, posebno u godinama koje dolaze. Treba zapamtiti – podaci su zlato. Bicikl je prevozno sretstvo sa dva ili vise tockova, koje se pokrece  djelovanjem snage misica na pedale. Velo je najcesci naziv za bicikl sirom svijeta. Rijec je nastala od latinske rijeci Veloxpes sto bi u prevodu znacilo “brza noga” ili francuski Velocipede sto bi takodje znacilo “brza noga”. Medjutim,  krenimo od pocetka. Bicikl je prvo (uspjesno) individualno prevozno sredstvo, naravno ukoliko se izuzme jahanje zivotinja. Karl Drais, njemacki ucitelj koji je zivio u Francuskoj, patentirao je 1817 trkacu masinu, kako je on sam nazivao svoj izum na dva tocka bez pedala. Kasnije, njemu u cast, trkaca masina mijenja ima u Draisine. Draisine se smatra najstarijim biciklom kojim je moguce upravljati. Tada je predstavljena kao alternativa jahanju, s obzirom da je predhodnih par godina bilo susno i zitarice su slabo rodile, sto je uticalo na povisenje cijene hrane za konje. Mnogi su kopirali Draisine i bila je veoma popularna, sve dok cijena zitarica ponovo nije opala. Niske cijene zitarica i izostatak razvoja Daisine uticali su da nakon par godina padne u zaborav.                                                                                                                                                                        Tek pocetkom industrijalizacije u drugoj polovini 19. Stoljeca ponovo se pojavljuje potreba za trkacim masinama. Ubrzo se na trkace masine ugradjuje pogon na pedale. Pedale su kao patent 1866 godine registrovane u Americi od strane dvojice francuza, Pierra Michaux-a i Pierra Lallement-a. Razvoj se nastavio i do kraja stoljeca trkace masine su bile veoma slicne danasnjem biciklu. Jedino su mjenjaci brzina cekali svoje otkrice do pocetka 20. Stoljeca. Faze razvoja od visokog bicikla sa jednim velikim i jednim malim tockom (1865.god.) pa do standardnog oblika bicikla (1890.god.) koji je zadrzan do danas znatno su uticale na pocetak razvoja Automobila. Bicikl pored toga sto je bio prvo individualno prevozno sredstvo, bio je i najjeftiniji nacin prevoza. Prva masovna upotreba bicikla primjecena je u Evropi pocetkom 20. Stoljeca, kao posljedica industrijalizacije, jer su radnici radeci u fabrikama morali prelaziti dalji put do radnog mjesta. Da bi radnici bili u mogucnoti priustiti si bicikl takodjer je zaslusna industrija, tek sa masovno proizvodnjom pale su cijene i tako je bicikl postao dostupan sirim masama. Tek 1960-ih godina smanjuje se upotreba bicikla i zamjenjuje  motorima ili automobilima. U siromasnim zemljama bicikl ostaje jos neko vrijeme glavno prevozno sredstvo. Za vrijeme nestasice ulja na svjetskom trzistu 1970-ih godina i usljed rasta ekoloske svijesti bicikl se ponovo vraca na ulice. Sve veci znacaj ima u gradskom saobracaju, sto je od javnog interesa. Kao posljedica toga, sirom Evrope prave se posebne trake za bicikla, parking mjesta, kao i mogucnost iznajmljivanja i koristenja gradskog bicikla. Kao pozitivan primjer moze se spomenuti Kopenhagen i Munster, u kojima postotak biciklista u saobracaju iznosi preko nevjerovatnih 35 %. Sirom planete organizuju se masovne vozne kroz gradove pod nazivom Critical Mass, protestujuci tako protiv zagadjivanja zraka od strane automobila i za bolje uslove za bicikliste. Critical Mass se i danas redovno (najmanje jednom mjesecno) odrzava u gradovima sirom Evrope i svijeta. Licno sam imao priliku ucestvovati na Critical Mass u nekoliko gradova, sto predstavlja jedinstven dozivljaj. S obzirom da su to organizovane voznje, policija osigurava nesmetan prolazak biciklista zatvarajuci promet vozilima. O pokretu Critical Mass ce biti govora u jednom od slijedecih textova. Bicycle_evolutionStatisticari su dosli do podatka da u Evropskim metropolama poput Berlina, Amsterdama, Kopenhagena, Beca, Praga itd. vise od 50% voznji su krace od 5 kilometara, sto znaci da bi se mogle bez problema obaviti biciklom i tako ustedjeti novac i uciniti nesto dobro za okolinu. Uzme li se u obzir da su kod nas gradovi mnogo manji dolazimo do zakljucka da voznje koje obavljamo automobilom su vjerovatno krace. Treba uzeti par minuta vremena i razmisliti o ovome. Knjige o daljim putovanjima biciklom postoje jos od kraja 19. Stoljeca, pa onda nije nikakvo cudo sto danas ljudi krecu na put oko svijeta biciklom ili odlaze na godisnje odmore na biciklu. Vazno je probuditi svijest o ocuvanju planete, a pored toga cinite nesto za svoje zdravlje i izgled.Bicikl je najucinkoviti nacin da se ispuni u Kyoto-Protokoll-u dogovorena redukcija CO2, jer ne postoji drugo prevozno sredstvo koje toliko stedi energiju i cuva okolinu";
		String tekst6 = "Kinologija je znanost o psima i njihovim pasminskim svojstvima. Istoimenim predmetom se educira studente o podrijetlu pasa, tijeku pripitomljavanja i njihovoj sistematskoj pripadnosti. Upućuje ih se u morfološko anatomske i biološke karakteristike pasa s osnovama reprodukcije, skotnosti, štenjenja, hranidbenim potrebama, ishranom i smještajem. Nezaobilazni dio su odgoj i obuka pasa. Definira se značenje domaćih i međunarodnih kinoloških asocijacija i njihovo razvrstavanje pojedinih pasmina u odgovarajuće skupine i pod skupine. Unutar definiranih skupina prezentiraju se pasminske karakteristike kao i međunarodni standardi i radne karakteristike s isticanjem Hrvatskih autohtonih pasmina. Stječu se dostatna znanja i vještine za nastavak studiranja kao i za obavljanje poslova unutar struke. Programski dijelovi modula su: Osnovi anatomije, fiziologije, biologije i ekologije pasa. Podrijetlo pasa kroz povijest. Od prethistorijskih pasa, podrijetla vrsta do nastajanja novih pasmina. Razmnožavanje pasa od spolnog dozrijevanja, tjeranja, parenja, nošenja i poroda do uzgoja legla s istaknutim kritičnim razdobljima. Osnovi nasljeđivanja s osnovnim metodama uzgoja i suvremenim metodama DNA analiza. Socijalizacija, odgoj i ponašanje pasa Hranidba pasa s naglaskom na različitost određenih pasminskih i radnih kategorija. Smještaj i njega pasa s načelima njege i smještaja. Najučestalije bolesti pasa s mjerama prevencije. Međunarodne kinološke asocijacije, FCI podjela pasa u skupine s njihovim karakteristikama. Teorijske spoznaje se kvantitativno i kvalitativno nadopunjuju eksperimentalnim, terenskim i vježbama u praktikumu. Pretpostavke za polaganje završnog usmenog ispita su redovnost sudjelovanja na nastavnim programima, (predavanja, terenske vježbe, seminari), izrada pisanih seminara i uspješno rješavanje parcijalnih pismenih testova znanja.";
		String tekst7 = "Postoje dve kategorije neuronskih mreža: veštačke i biološke. Predstavnik bioloških neuronskih mreža je nervni sistem živih bića. Veštačke neuronske mreže su po strukturi, funkciji i obradi informacija slične biološkim neuronskim mrežama, ali se radi o veštačkim tvorevinama. Neuronska mreža u računarskim naukama predstavlja veoma povezanu mrežu elemenata koji obrađuju podatke. One su sposobne da izađu na kraj sa problemima koji se tradicionalnim pristupom teško rešavaju, kao što su govor i prepoznavanje oblika. Jedna od važnijih osobina neuronskih mreža je njihova sposobnost da uče na ograničenom skupu primera. Biološke neuronske mreže su daleko komplikovanije od svojih matematičkih modela koji se koriste za veštačke neuronske mreže. Kada se govori o neuronskim mrežama, misli se na “veštačke neuronske mreže” (engleski termin Artificial Neural Networks), zbog toga što se uglavnom govori o modelima neuronskih mreža, realizovanim na računarima. Veštačke neuronske mreže su kolekcija matematičkih modela koji simuliraju neke od posmatranih osobina bioloških nervnih sistema i povlače sličnosti sa prilagodljivim biološkim učenjem. Sačinjene su od velikog broja međusobno povezanih neurona (obrađujućih elemenata) koji su, slično biološkim neuronima, povezani svojim vezama koje sadrže propusne (težinske) koeficijente, koje su po ulozi slične sinapsama. Učenje se kod bioloških sistema obavlja putem regulisanja sinaptičkih veza koje povezuju aksone i dendrite neurona. Učenje tipičnih događaja putem primera se ostvaruje preko treninga ili otkrića do tačnih setova podataka ulaza-izlaza koji treniraju algoritam ponavljanjem podešavajući propusne (težinske) koeficijente veza (sinapse). Ove veze memorišu znanje neophodno za rešavanje specifičnog problema. Većina neuronskih mreža ima neku vrstu pravila za “obučavanje”, čime se koeficijenti veza između neurona podešavaju na osnovu ulaznih podataka. Drugim rečima, neuronske mreže “uče” preko primera (kao što deca uče da prepoznaju konkretan predmet, objekat, proces ili pojavu preko odgovarajućih primera) i poseduju sposobnost za generalizaciju posle trening podataka. Danas se neuronske mreže primenjuju za rešavanje sve većeg broja svakodnevnih problema sa značajnom kompleksnošću. Pokazuju dobre rezultate prilikom predviđanja i modelovanja sistema gde fizički procesi nisu jasni ili su veoma kompleksni. Prednost neuronskih mreža leži u visokoj elastičnosti prema poremećajima u ulaznim podacima i u sposobnosti da uči. Neuronska mreža često uspešno rešava probleme koji su previše kompleksni za konvencionalne tehnologije (na primer, problem koji nema algoritamsko rešenje ili za koji je algoritam previše komplikovan da bi bio pronađen) i one su često dobra pratnja problemima koje ljudi rešavaju.";
		
		esRecenzentRepository.save(new RecenzentDTO(rec1.getId(), rec1.getIme(), rec1.getPrezime(), rec1.getEmail(), "Nacionalna Geografija", setGeoPointLokacija(rec1.getLokacija()), tekst1));
		esRecenzentRepository.save(new RecenzentDTO(rec2.getId(), rec2.getIme(), rec2.getPrezime(), rec2.getEmail(), "Mašinsko Učenje 101", setGeoPointLokacija(rec2.getLokacija()), tekst2));
		esRecenzentRepository.save(new RecenzentDTO(rec3.getId(), rec3.getIme(), rec3.getPrezime(), rec3.getEmail(), "Nacionalna Geografija, Mašinsko Učenje 101", setGeoPointLokacija(rec3.getLokacija()), tekst3));
		esRecenzentRepository.save(new RecenzentDTO(rec4.getId(), rec4.getIme(), rec4.getPrezime(), rec4.getEmail(), "Nacionalna Geografija, Mašinsko Učenje 101", setGeoPointLokacija(rec4.getLokacija()), tekst4));
		esRecenzentRepository.save(new RecenzentDTO(rec5.getId(), rec5.getIme(), rec5.getPrezime(), rec5.getEmail(), "Nacionalna Geografija, Mašinsko Učenje 101", setGeoPointLokacija(rec5.getLokacija()), tekst5));
		esRecenzentRepository.save(new RecenzentDTO(rec6.getId(), rec6.getIme(), rec6.getPrezime(), rec6.getEmail(), "Nacionalna Geografija", setGeoPointLokacija(rec6.getLokacija()), tekst6));
		esRecenzentRepository.save(new RecenzentDTO(rec7.getId(), rec7.getIme(), rec7.getPrezime(), rec7.getEmail(), "Nacionalna Geografija", setGeoPointLokacija(rec7.getLokacija()), tekst7));
*/
//UREDNICI
		Urednik urednikNG = new Urednik(null, "urednikNG", "u", "Mika", "Mikić", "ninamns1095@gmail.com", new Point(45.25167, 19.83694), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), casNG, "dr");
		urednikNG = urednikRepository.save(urednikNG);
		casNG.setUrednik(urednikNG);
		casNG = casopisRepository.save(casNG);
	
		Urednik urednikMU = new Urednik(null, "urednikMU", "u", "Pera", "peric", "pera@gmail.com", new Point(new Double(43.32472), new Double(21.90333)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), casMU, "dr");
		urednikMU = urednikRepository.save(urednikMU);
		casMU.setUrednik(urednikMU);
		casMU = casopisRepository.save(casMU);
		
		User urednikNGMika = identityService.newUser(urednikNG.getKorisnickoIme());
		urednikNGMika.setFirstName(urednikNG.getIme());
		urednikNGMika.setLastName(urednikNG.getPrezime());
		urednikNGMika.setEmail(urednikNG.getEmail());
		urednikNGMika.setPassword(urednikNG.getLozinka());
		identityService.saveUser(urednikNGMika);			
		identityService.createMembership("urednikNG", "urednik");
		
		User urednikMUPera = identityService.newUser(urednikMU.getKorisnickoIme());
		urednikMUPera.setFirstName(urednikMU.getIme());
		urednikMUPera.setLastName(urednikMU.getPrezime());
		urednikMUPera.setEmail(urednikMU.getEmail());
		urednikMUPera.setPassword(urednikMU.getLozinka());
		identityService.saveUser(urednikMUPera);	
		identityService.createMembership("urednikMU", "urednik");
//
		
		
		
//UREDNIK_NO
		
		UrednikNO urednikNONG = new UrednikNO(null, "urednik1", "urednik1", "Bojan", "Bojanić", "bbojanic@live.com", new Point(new Double(45.25167), new Double(19.83694)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), new ArrayList<Casopis>(), "dr",  new ArrayList<NaucnaOblast>(), casNG, naucnaOblast1);
		urednikNONG = urednikNORepository.save(urednikNONG);
		UrednikNO urednik1NONG = new UrednikNO(null, "urednik2", "urednik2", "Mirko", "Mirkić", "mmirko@live.com", new Point(new Double(43.32472), new Double(21.90333)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), new ArrayList<Casopis>(), "dr",  new ArrayList<NaucnaOblast>(), casNG, naucnaOblast2);
		urednik1NONG = urednikNORepository.save(urednik1NONG);
		UrednikNO urednikNOMU = new UrednikNO(null, "urednik3", "urednik3", "Marko", "Marković", "mmarko@live.com", new Point(new Double(44.786568), new Double(20.448922)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), new ArrayList<Casopis>(), "prof dr",  new ArrayList<NaucnaOblast>(), casMU, naucnaOblast0);
		urednikNOMU = urednikNORepository.save(urednikNOMU);
//camunda		
		User urednikNONGC = identityService.newUser(urednikNONG.getKorisnickoIme());
		urednikNONGC.setFirstName(urednikNONG.getIme());
		urednikNONGC.setLastName(urednikNONG.getPrezime());
		urednikNONGC.setEmail(urednikNONG.getEmail());
		urednikNONGC.setPassword(urednikNONG.getLozinka());
		identityService.saveUser(urednikNONGC);			
		identityService.createMembership(urednikNONG.getKorisnickoIme(), "urednikNO");
		
		User urednik1NONGC = identityService.newUser(urednik1NONG.getKorisnickoIme());
		urednik1NONGC.setFirstName(urednik1NONG.getIme());
		urednik1NONGC.setLastName(urednik1NONG.getPrezime());
		urednik1NONGC.setEmail(urednik1NONG.getEmail());
		urednik1NONGC.setPassword(urednik1NONG.getLozinka());
		identityService.saveUser(urednik1NONGC);			
		identityService.createMembership(urednik1NONG.getKorisnickoIme(), "urednikNO");
		
		User urednikNOMUC = identityService.newUser(urednikNOMU.getKorisnickoIme());
		urednikNOMUC.setFirstName(urednikNOMU.getIme());
		urednikNOMUC.setLastName(urednikNOMU.getPrezime());
		urednikNOMUC.setEmail(urednikNOMU.getEmail());
		urednikNOMUC.setPassword(urednikNOMU.getLozinka());
		identityService.saveUser(urednikNOMUC);			
		identityService.createMembership(urednikNOMU.getKorisnickoIme(), "urednikNO");
		
//RADOVI
		ArrayList<Korisnik> recenzenti1NG = new ArrayList<Korisnik>();
		recenzenti1NG.add(rec3);
		recenzenti1NG.add(rec4);
		ArrayList<Korisnik> recenzenti2NG = new ArrayList<Korisnik>();
		recenzenti2NG.add(rec3);
		recenzenti2NG.add(rec2);
		ArrayList<Korisnik> recenzenti3NG = new ArrayList<Korisnik>();
		recenzenti3NG.add(rec4);
		recenzenti3NG.add(rec2);
		
		ArrayList<Korisnik> recenzenti1MU = new ArrayList<Korisnik>();
		recenzenti1MU.add(rec2);
		recenzenti1MU.add(rec3);
		ArrayList<Korisnik> recenzenti2MU = new ArrayList<Korisnik>();
		recenzenti2MU.add(rec4);
		recenzenti2MU.add(rec5);
		ArrayList<Korisnik> recenzenti3MU = new ArrayList<Korisnik>();
		recenzenti3MU.add(rec4);
		recenzenti3MU.add(rec2);
		
		Rad radMU1 = new Rad(null, "Оцена популарности станице према објектима у околини", "Горана Гојић, gorana.gojic@uns.ac.rs; Ангелина Вујановић, avujanovic@uns.ac.rs", autorrad1MU, "рударење подaтака, стабло одлучивања, корелација, статистика, Capital Bikeshare, станице бицикaла, промет, модел систем", "D:\\Users\\Nina\\Desktop\\udd\\rad1.pdf", "C:\\Users\\nina.miladinovic\\git\\NaucnaCentrala\\src\\main\\resources\\static\\assets\\pdf\\rad1.pdf", naucnaOblast0, StatusRada.PRIHVACEN, recenzenti1MU);
		radMU1 = radRepository.save(radMU1);//rad1
		Rad radMU2 = new Rad(null, "Predikcija cene nekretnine na osnovu teksta oglasa, slika i geografske lokacije nekretnine", "Mladen Vidović, mladenvidovic@uns.ac.rs; Ivan Radosavljević, ivanradosavljevic@uns.ac.rs", autorrad2MU, "stanovi, cene, predikcija, slike, nekretnine, regresija", "D:\\Users\\Nina\\Desktop\\udd\\rad2.pdf", "C:\\Users\\nina.miladinovic\\git\\NaucnaCentrala\\src\\main\\resources\\static\\assets\\pdf\\rad2.pdf", naucnaOblast0, StatusRada.PRIHVACEN, recenzenti2MU);
		radMU2 = radRepository.save(radMU2);//rad2
		Rad radMU3 = new Rad(null, "Одређивање степена конзумације алкохола код средњошколаца на основу социјалних фактора", "Милош Марић, milososig@gmail.com; Дражен Ђанић, djanic.home@gmail.com", autorrad3MU, "конзумација алкохола, млади, фактори, предвиђање, Support vector machines, Наивни Бајес, рударење податак ", "D:\\Users\\Nina\\Desktop\\udd\\rad3.pdf", "", naucnaOblast0, StatusRada.PRIJAVLJEN, new ArrayList<Korisnik>());
		radMU3 = radRepository.save(radMU3);//rad3
		Rad radMU4 = new Rad(null, "Uticaj Microsoft-a na razvoj kompjutera i kompjuterskog softvera", "Filip Jerenić", autorrad3MU, "D:\\Users\\Nina\\Desktop\\udd\\rad8.pdf", "", "Microsoft, uticaj, kompjuteri, razvoj", naucnaOblast3, StatusRada.PRIHVACEN, recenzenti3MU);
		radMU4 = radRepository.save(radMU4);//rad8
		
		ArrayList<Rad> radoviMU = new ArrayList<Rad>();
		radoviMU.add(radMU1);
		radoviMU.add(radMU2);
		radoviMU.add(radMU3);
		radoviMU.add(radMU4);
		izd2MU.setRadovi(radoviMU);
		izd2MU = izdanjeRepository.save(izd2MU);
		
		Rad radNG1 = new Rad(null, "Izolacija bakterija rezistentnih na metale iz zemljišta", "", autorrad1NG, "zagađenje zemljišta, teški metali, bioremedijacija, izolacija bakterija", "D:\\Users\\Nina\\Desktop\\udd\\rad6.pdf", "C:\\Users\\nina.miladinovic\\git\\NaucnaCentrala\\src\\main\\resources\\static\\assets\\pdf\\rad6.pdf", naucnaOblast1, StatusRada.PRIHVACEN, recenzenti1NG);
		radNG1 = radRepository.save(radNG1);//rad6
		Rad radNG2 = new Rad(null, "Anatomsko-fiziološke osnove reprodukcije domaćih životinja", "", autorrad1NG, "reprodukcija, razvoj, polne ćelije, ciklus, sisar, živina, stoka", "D:\\Users\\Nina\\Desktop\\udd\\rad4.pdf", "", naucnaOblast2, StatusRada.PRIJAVLJEN, new ArrayList<Korisnik>());
		radNG2 = radRepository.save(radNG2);//rad4
		Rad radNG3 = new Rad(null, "Očuvanje genetičkih resursa autohtonih rasa domaćih životinja u Srbiji", "Darko Drobnjak, ddrobnjak@edu.rs; Milivoje Urošević, m.urosevic@edu.rs", autorrad2NG, "životinjski resursi, autohtone rase, očuvanje, stočarska proizvodnja", "D:\\Users\\Nina\\Desktop\\udd\\rad5.pdf", "", naucnaOblast2, StatusRada.PRIJAVLJEN, new ArrayList<Korisnik>());
		radNG3 = radRepository.save(radNG3);//rad5
		Rad radNG4 = new Rad(null, "Upravljanje organskim otpadom beogradskih pijaca", "", autorrad3NG, "organski otpad, bio otpad, gradske pijace, anaerobna digestija, kompostiranje, prirodno đubriv", "D:\\Users\\Nina\\Desktop\\udd\\rad7.pdf", "C:\\Users\\nina.miladinovic\\git\\NaucnaCentrala\\src\\main\\resources\\static\\assets\\pdf\\rad7.pdf", naucnaOblast1, StatusRada.PRIHVACEN, recenzenti3NG);
		radNG4 = radRepository.save(radNG4);//rad7
		Rad radNG5 = new Rad(null, "Razvoj geografije stanovništva od antropogeografskog do prostorno-analitičkog pristupa", "Milena Spakovski, m.spakovski@live.com; Danica Šantić, danicasantic@gmail.com", autor0, " geografija stanovništva, antropogeografija, društvena geografija, migracije, demografija", "D:\\Users\\Nina\\Desktop\\udd\\rad9.pdf", "", naucnaOblast1, StatusRada.PRIHVACEN, recenzenti3NG);
		radNG5 = radRepository.save(radNG5);//rad7
		
		ArrayList<Rad> radoviNG = new ArrayList<Rad>();
		radoviNG.add(radNG1);
		radoviNG.add(radNG2);
		radoviNG.add(radNG3);
		izd2NG.setRadovi(radoviNG);
		izd2NG = izdanjeRepository.save(izd2NG);
		
		
	}
	
/*	private GeoPoint setGeoPointLokacija(Point lokacija) {
		return new GeoPoint(lokacija.getX(), lokacija.getY());
	}*/
}

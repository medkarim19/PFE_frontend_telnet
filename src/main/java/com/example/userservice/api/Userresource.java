package com.example.userservice.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.userservice.domain.*;
import com.example.userservice.repo.*;
import com.example.userservice.service.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


@RestController
@RequiredArgsConstructor
@RequestMapping ("/api")
public class Userresource {
	private final Userservice userservice ;
	private final JavaMailSender mailSender;
	private final UserRepo userrepo ;
	@PersistenceContext // o
	EntityManager em;
	@Autowired
	private  FilesStorageServiceImpl  storageService;
	@Autowired
	ImageRepository imageRepository;
	private final ProjectRepo projectrepo;
	private final ProjetService projetservice ;
	private final SourceService sourceservice ;
	private final TypeService Typeservice ;
	private final RoleRepo role;
	private final Processus_impactesService pp;
	private final RisqueService risqueservice ;
	private final RisqueRepo repoo;
	private final Plan_Attunation_Service pla;
	private final Plan_ContingenceService plc;
	private final Action_AttunationService actioatuunation;
	private final Action_ContingenceService actiocontingence ;
	private final CategorieService  ca ;
	private final StratigieService st ;

	@GetMapping ("/users")
	public ResponseEntity<List<User>>getusers() {
		return ResponseEntity.ok().body(userservice.getUser() );
	}
	@GetMapping ("/roles")
	public ResponseEntity<List<Role>>getroles() { return ResponseEntity.ok().body(userservice.getRole() );
	}
	@GetMapping ("/admin/project")
	public ResponseEntity<List<Projet>>getProject( ){
		return ResponseEntity.ok().body(projectrepo.findAll() );
	}
	@GetMapping ("/user/{email}")
	public ResponseEntity<Boolean>isUserExist(@PathVariable("email") String email) {
			User user=userrepo.findByEmail(email);
		return ResponseEntity.ok().body(user!=null);
	}
	@Transactional
	@DeleteMapping("/projet/{id}")
	public ResponseEntity<?> deletePost(@PathVariable Long id) {

		projectrepo.deleteById(id);
		URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/projet").toUriString());
		return ResponseEntity.created(uri).body(projectrepo.findAll());
	}
	@PostMapping ("/projet/save")
	public ResponseEntity<Projet>saveProject(@RequestBody Ensembledesrole a) {
		URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/projet/save").toUriString());

		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
		LocalDate dt1 = LocalDate.parse(a.getDate());

		Projet p =new Projet (null,a.nameofentity,a.getDescription(),a.nameofcreator,null,dt1,null,null);


		//projectrepo.save(p).setDateofcreation(dt1);
		return ResponseEntity.created(uri).body(projectrepo.save(p));
	}
	@GetMapping ("/get/projet/{name}")
	public ResponseEntity<Projet>getProjet(@PathVariable("name")String p) {
		Projet x =projectrepo.findByName(p);
		return ResponseEntity.ok().body( x);
	}

	@GetMapping ("/get/user/{name}")
	public ResponseEntity<User>getUser(@PathVariable("name")String p) {
		return ResponseEntity.ok().body(userservice.getUser(p) );
	}

	@PostMapping ("/user/save")
	public ResponseEntity<User>saveUser(@RequestBody User user) {
		URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save").toUriString());
		return ResponseEntity.created(uri).body(userservice.saveUser(user));
	}

	@Transactional
	@PutMapping("/user/update/{username}")
	public ResponseEntity<User>updateUser(@RequestBody User user, @PathVariable("username") String username) {
		ResponseEntity<User> ok = ResponseEntity.ok(userservice.updateUser(user, username));
		return ok;
	}
	@PutMapping("/role/update/{name}")
	public ResponseEntity<Role>updateRole(@RequestBody Role role, @PathVariable("name") String name) {
		ResponseEntity<Role> ok = ResponseEntity.ok(userservice.updateRole(role, name));
		return ok;
	}
	@DeleteMapping("/role/delete/{name}")
	public Map<String, Boolean> deleteRole(@PathVariable(value = "name") String name)
	{
		userservice.deleteRole(name);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@PostMapping ("/role/save")
	public ResponseEntity<Role>saveRole(@RequestBody Role role) {
		URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save").toUriString());
		return ResponseEntity.created(uri).body(userservice.saveRole(role));
	}
	@PostMapping ("/role/addtouser")
	public ResponseEntity<?>addRoleTouser(@RequestBody Form form) {
		userservice.addRoleToUser(form.getToname() ,form.getTheaddname());
		//@PathVariable("idoproject")Long idoproject
		return ResponseEntity.ok().build();
	}
	@Transactional
	@PostMapping ("/role/user/project")
	public ResponseEntity<?>addUserToProjectwithrole(@RequestBody Ensembledesrole form) {
		URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/user/project").toUriString());
		Projet a=	projectrepo.findByName(form.getNameofentity());
		//Role x= role.findByName(form.description);
		User p =userservice.getUser(form.nameofcreator);
		ArrayList <Role>roles1=new ArrayList<Role>(100);
		form.getRoles1().stream().forEach(v->roles1.add(role.findByName(v)));
		a.getUsesswithroles().put(p.getUsername(), form.getRoles1());
		return ResponseEntity.created(uri).body(projectrepo.findAll());
	}
	@GetMapping ("/role/addtousker")
	public void refreshtoken(HttpServletRequest request, HttpServletResponse response) {
		String autho =request.getHeader(HttpHeaders.AUTHORIZATION);
		if (autho!=null&& autho.startsWith("Bearer ")) {
			System.out.println ("authopp");
			try {
				String token=autho.substring("Bearer ".length());
				
				Algorithm algo=	Algorithm.HMAC256("secret".getBytes());
				
				JWTVerifier verifier=JWT.require(algo).build();
				DecodedJWT decodejwt=verifier.verify(token);
				
				String username=decodejwt.getSubject();
				System.out.println (username);
				String[] roles=decodejwt.getClaim("roles").asArray(String.class);
				Collection<SimpleGrantedAuthority>authori=new ArrayList <>();
				Arrays.stream(roles).forEach(role->authori.add(new SimpleGrantedAuthority(role)));
				UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(username,null,authori);
				SecurityContextHolder.getContext().setAuthentication(auth);
				//filterChain.doFilter(request, response);
			}catch(Exception exception) {
				//log.error("Eroor login in  {}",exception.getMessage());
				response.setHeader("error", exception.getMessage());
				//response.sendError(HttpStatus.FORBIDDEN.value());
				Map <String,String>errors=new HashMap<>();
				errors.put("error_message", exception.getMessage());
				//System.out.println (exception.getMessage());
				//tokens.put("refresh_token", refresh_token);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				//new ObjectMapper().writeValue(response.getOutputStream(), errors);
			}
		}
		else {
			throw new RuntimeException("Refresh token is missing");
		
	}
	
}
	@GetMapping ("/users1/project/{name}")
	public ResponseEntity<List<Projet>>usersnotinprject(@PathVariable("name")String pname){
		Projet p =  projectrepo.findByName(pname);
		List a = userservice.getUser().stream().filter(u->!(p.getUsesswithroles().containsKey(u.getUsername()))).toList();
		return ResponseEntity.ok().body(a );
	}

	@DeleteMapping("/deleteuserr/projet")
	public ResponseEntity<?>removeresource(@RequestBody Ensembledesrole form)
	{
		Projet p =  projectrepo.findByName(form.getNameofentity());
		p.getUsesswithroles().remove(form.getNameofcreator());
		return ResponseEntity.ok().body(p);



	}

	@Transactional
	@PutMapping ("/update2/project/{id}")
	public ResponseEntity<?>updateproject2(@RequestBody Ensembledesrole form,@PathVariable Long id) throws Exception {
		Optional<Projet>projetop=Optional.ofNullable(projectrepo.findById(id).orElseThrow(()->new Exception("Projet not found - " )));
		Projet a =projetop.get();
		LocalDate dt1 = LocalDate.parse(form.date);
		a.setName(form.nameofentity);
		a.setDateofcreation(dt1);
		a.setDescription(form.description);
		return ResponseEntity.ok().body(a);
	}
	@PostMapping ("/projet1/save")
	public ResponseEntity<List<Projet>>saveProject1(@RequestBody Ensembledesrole a) {
		URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/projet/save").toUriString());
		System.out.println (a.toString());
		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
		projetservice.Saveprojet( a.nameofentity, a.getDescription(),a.date, a.nameofcreator);
		//projectrepo.save(p).setDateofcreation(dt1);
		return ResponseEntity.created(uri).body(projectrepo.findAll());
	}
	@GetMapping ("/user/project/{id}")
	public ResponseEntity<List<User>>userinprojet(@PathVariable("id")long id) throws Exception{
		return ResponseEntity.ok().body(projetservice.getAllusersinproject(id));

	}
	@GetMapping ("/user2/project/{id}")
	public ResponseEntity<List<User>>usernotinprojet(@PathVariable("id")long id) throws Exception{
		return ResponseEntity.ok().body(projetservice.getAllusersnotinproject(id));

	}
	@Transactional
	@PutMapping ("/user2/get2")
	public void updateprojectrole(@RequestBody Ensembledesrole form) throws Exception{
		Long a=Long.parseLong(form.getId1());
		Long ab=Long.parseLong(form.getId2());

		Long x=projetservice.Updaterole(a, ab,form.roles1);
		ResponseEntity.ok().body(x);
	}
	@Transactional
	@PutMapping ("/add/project1")
	public void Addusertoproject(@RequestBody Ensembledesrole form) throws Exception {
		Long a=Long.parseLong(form.getId1());
		Long ab=Long.parseLong(form.getId2());
		projetservice.addUserInProject(a, ab, form.roles1);

	}

	@GetMapping ("/admin")
	public void createadmin() throws Exception{
		projectrepo.update((long) -1, "admin");
		ResponseEntity.ok();
	}
	@PutMapping ("/add/admin/{id}")
	public void Addadmin(@PathVariable Long id) throws Exception {

		projetservice.addAdmin(id);

	}
	@PutMapping ("/get/admin/{id}")
	public ResponseEntity<?> isadmin(@PathVariable Long id) throws Exception {

		Boolean t=userservice.isAdmin(id);
		return ResponseEntity.ok().body(t);

	}
	@GetMapping ("/all/{id}")
	public  ResponseEntity<List<Projet>>  GetProject (@PathVariable Long id){
		List<Projet>a=userservice.getAllProjet(id);
		return ResponseEntity.ok().body(a);
	}
	@DeleteMapping("/delete")

	public void delete (@RequestBody Ensembledesrole form) throws Exception {
		Long a=Long.parseLong(form.getId1());
		Long ab=Long.parseLong(form.getId2());
		projetservice.deleteUserfromProjet(a, ab);
	}
	@DeleteMapping("/delete1/{id}")

	public void delete (@PathVariable Long id) throws Exception  {
		projetservice.deleteProjet(id);
	}
	@DeleteMapping("/deleteuser/{id}")

	public void deleteUser(@PathVariable Long id) throws Exception  {
		userservice.deleteUser(id);
	}
	@PostMapping ("/savesource/{valeur}")
	public ResponseEntity<?>savSource(@PathVariable String valeur) {

		sourceservice.saveSouce(valeur);
		return ResponseEntity.ok().build();
	}
	@PostMapping ("/savetype/{valeur}")
	public ResponseEntity<?>savetype(@PathVariable String valeur) {

		Typeservice.saveType(valeur);
		return ResponseEntity.ok().build();
	}
	@PostMapping ("/saveAllSource")
	public ResponseEntity<?>saveAllSource() {

		sourceservice.saveAll();
		return ResponseEntity.ok().build();
	}
	@PostMapping ("/saveAllType")
	public ResponseEntity<?>saveAlltype() {
		URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save").toUriString());
		Typeservice.saveAllTyper();
		return ResponseEntity.ok().build();
	}
	@PostMapping ("/saveAllProcessus")
	public ResponseEntity<?>saveAllProcessus() {
		URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save").toUriString());
		pp.add_all_Processus_impacte();
		return ResponseEntity.ok().build();
	}
	@GetMapping ("/getType")
	public ResponseEntity<List<Type>>Gettype() throws Exception{
		return ResponseEntity.ok().body( Typeservice.getAllTypes());

	}
	@GetMapping ("/getSource")
	public ResponseEntity<List<Source>>GettSource() throws Exception{
		return ResponseEntity.ok().body( sourceservice.getAllSource());

	}
	@GetMapping ("/geAllProcessus")
	public ResponseEntity<List<Processus_impactes>>GettProcessus() throws Exception{
		return ResponseEntity.ok().body( pp.getAll());

	}
	@GetMapping("/getPourcentage")
	public ResponseEntity<?>GetPourcentage(){
		return  ResponseEntity.ok().body(risqueservice.get_pourcentage());
	}
	@GetMapping("/getCalcul")
	public ResponseEntity<?>GetCalcul(){
		return  ResponseEntity.ok().body(risqueservice.calc1());
	}

	@PostMapping ("/processus/save/{valeur}")
	public ResponseEntity<?>saveProcessus(@PathVariable("valeur") String valeur,@RequestBody Form f){
		pp.add_Processus_impacte(valeur,f.getList());
		return ResponseEntity.ok().build();
	}
	@PutMapping ("/updatee33/{id}")
	public void update33(@PathVariable Long id,@RequestBody Form f ) throws Exception {
		this.risqueservice.update22(id, f.getId1(), f.getId2(),f.getMesurerisque());
	}
	@GetMapping ("/get/risquebyid/{id}")
	public ResponseEntity<?> getrisque(@PathVariable Long id) throws Exception {


		return ResponseEntity.ok().body( risqueservice.getrisque(id));

	}
	@DeleteMapping("/processus/delete/{id}")
	public void deleteP(@PathVariable Long id) throws Exception  {
		pp.deleleProcessus(id);
	}
	@PutMapping("processus/update/{id}")
	public ResponseEntity<Processus_impactes>updateP( @PathVariable("id") Long id,@RequestBody Form f) throws Exception {
		ResponseEntity<Processus_impactes> ok = ResponseEntity.ok(pp.updateProcessus(id,f.getValeur(),f.getList()));
		return ok;
	}
	@GetMapping("processus/phase/{id}")
	public List<String>getPhase(@PathVariable("id") Long id) throws Exception {
		return  pp.getPhase(id);
	}
	@PostMapping ("/saveRisque")
	public ResponseEntity<Risque>saveRisque(@RequestBody Risque r) throws Exception {
		//	System.out.print(r.getGravite()) ;
		// System.out.print(r.toString());



		return ResponseEntity.ok().body(risqueservice.saveRisque(r));
	}
	@GetMapping ("/geAlRisque")
	public ResponseEntity<List<Risque>>Gettalrisque() throws Exception{
		return ResponseEntity.ok().body(risqueservice.getAll());

	}
	@DeleteMapping("/deleterisque/{id}")

	public void deleterisque(@PathVariable Long id) throws Exception  {
		risqueservice.deleterisque(id);
	}
	@DeleteMapping ("/roleuser")
	public List<Role>GetRole(@RequestBody Ensembledesrole form) throws Exception{
		System.out.println (form.getId1()+"hajouravvvvvvvvvvvvvvvvvvvvvvvbaa");		 Long a=Long.parseLong(form.getId1());
		Long ab=Long.parseLong(form.getId2());
		return (projetservice.roleuserinproject(a, ab));

	}
	@GetMapping ("/getAllRisque/{id}")
	public ResponseEntity<List<Risque>>Gettalrisq(@PathVariable Long id) throws Exception{
		return ResponseEntity.ok().body(risqueservice.gettAllrisqueforuser(id));

	}
	@PostMapping ("/saveImpact/{id}")
	public ResponseEntity<?>saveImpacte(@PathVariable Long id,@RequestBody Impact t ) {

		risqueservice.saveorupdate(id, t);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/forgot_password")
	public String processForgotPassword(HttpServletRequest request, Model model) {
		System.out.println("email"+request.getParameter("email"));
		String email = request.getParameter("email");
		String token = RandomString.make(30);

		try {
			userservice.updateResetPasswordToken(token, email);
			String resetPasswordLink = utility.getSiteURL(request) + "/changepassword?token=" + token;
			sendEmail(email, resetPasswordLink);
			model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

		} catch (UsernameNotFoundException ex) {
			model.addAttribute("error", ex.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Error while sending email");
		}

		return "forgot_password_form";
	}
	@PostMapping("/send_password")
	public String processSendPassword(HttpServletRequest request, Model model) {
		System.out.println("email"+request.getParameter("email"));
		String email = request.getParameter("email");
		try {
			String resetPasswordLink = utility.getSiteURL(request) + "/changepassword?token=";
			sendEmailPassword(email, resetPasswordLink);
			model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

		} catch (UsernameNotFoundException ex) {
			model.addAttribute("error", ex.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Error while sending email");
		}

		return "forgot_password_form";
	}
	public void sendEmailPassword(String recipientEmail, String link)
			throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("telnetriskmanagement@gmail.com", "Risk Management");
		helper.setTo(recipientEmail);
		String pass=Userserviceimple.pwd;


		String subject = "Voici le lien pour récupérer vote mot de passe";

		String content = "<p>Bonjour,</p>"
				+ "<p>Bienvenue à notre application, ceci est votre mot de passe actuel</p>" + pass
				+ "<p>Cliquez sur le lien ci-dessous pour changer votre mot de passe :</p>"
				+ "<p><a href=\"" + link + "\">Changer mon mot de passe</a></p>"
				+ "<br>"
				+ "<p>Ignorez cet e-mail si vous ne souhaitez pas modifier votre mot de passe.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);
	}



	public void sendEmail(String recipientEmail, String link)
			throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("telnetriskmanagement@gmail.com", "Risk Management");
		helper.setTo(recipientEmail);

		String subject = "Voici le lien pour réinitialiser votre mot de passe";

		String content = "<p>Bonjour,</p>"
				+ "<p>Vous avez demandé la réinitialisation de votre mot de passe.</p>"
				+ "<p>Cliquez sur le lien ci-dessous pour changer votre mot de passe :</p>"
				+ "<p><a href=\"" + link + "\">Changer mon mot de passe</a></p>"
				+ "<br>"
				+ "<p>Ignorez cet e-mail si vous vous souvenez de votre mot de passe, "
				+ "ou si vous n'en avez pas fait la demande.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);
	}
	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model) throws Exception {
		String token = request.getParameter("token");
		String password = request.getParameter("password");
		Long id = userservice.getByResetPasswordToken(token);
		User user =userrepo.findById(id).orElseThrow(()->new Exception("not found"));
		model.addAttribute("title", "Reset your password");

		if (id == null) {
			model.addAttribute("message", "Invalid Token");
			return "message";
		} else {
			userservice.updatePassword(user, password);

			model.addAttribute("message", "You have successfully changed your password.");
		}

		return "message";
	}
	@PostMapping("/changepwd/{id}")
	public void changePassword(@PathVariable Long id,@RequestBody Form form) throws Exception {
		userservice.changepassword(id,form.getTheaddname(),form.getToname());

	}
	@PostMapping ("/saveActiona")
	public ResponseEntity<?>saveActionA(@RequestBody Action_Attenuation t ) throws Exception {

		actioatuunation.saveAction(t);
		return ResponseEntity.ok().build();
	}
	@DeleteMapping ("/deleteaa/{id}")
	public ResponseEntity<?>deleteaa(@PathVariable Long id ) throws Exception {

		actioatuunation.deleteAction(id);
		return ResponseEntity.ok().build();
	}
	@PutMapping("/updateaction/{id}")
	public ResponseEntity<?>update(@PathVariable Long id,@RequestBody Action_Attenuation c) throws Exception {
		actioatuunation.update(id,c);
		return ResponseEntity.ok().build();
	}
	@PostMapping ("/saveActionc")
	public ResponseEntity<?>saveActionAc(@RequestBody Action_Contingence t ) throws Exception {

		actiocontingence.saveAction(t);
		return ResponseEntity.ok().build();
	}
	@DeleteMapping ("/deleteac/{id}")
	public ResponseEntity<?>deleteac(@PathVariable Long id ) throws Exception {

		actiocontingence.deleteAction(id);
		return ResponseEntity.ok().build();
	}
	@PostMapping ("/saveplanc")
	public ResponseEntity<?>saveplanc(@RequestBody Plan_Contingence t ) throws Exception {

		plc.savePlan(t);
		return ResponseEntity.ok().build();
	}
	@DeleteMapping ("/deleteac1/{id}")
	public ResponseEntity<?>deletepc(@PathVariable Long id ) throws Exception {

		plc.deletePlan(id);
		return ResponseEntity.ok().build();
	}
	@PostMapping ("/saveplana")
	public ResponseEntity<?>saveplana(@RequestBody Plan_Attenuation t ) throws Exception {

		pla.Add(t);
		return ResponseEntity.ok().build();
	}
	@DeleteMapping ("/deletepa/{id}")
	public ResponseEntity<?>deletepa(@PathVariable Long id ) throws Exception {

		pla.delete(id);
		return ResponseEntity.ok().build();
	}
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			storageService.store(file);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}
	@GetMapping("/files")
	public ResponseEntity<List<ResponseFile>> getListFiles() {
		List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder
					.fromCurrentContextPath()
					.path("/files/")
					.path(dbFile.getId())
					.toUriString();

			return new ResponseFile(
					dbFile.getName(),
					fileDownloadUri,
					dbFile.getType(),
					dbFile.getId(),
					dbFile.getData().length);
		}).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}
	@GetMapping("/files/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable String id) {
		FileDB fileDB = storageService.getFile(id);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
				.body(fileDB.getData());
	}
	@GetMapping("/getplanatunation/{id}")
	public ResponseEntity<?> getunationplanat(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok().body( risqueservice.getPlan_Attunation(id));
	}
	@GetMapping("/getplanatunationprojet/{id}")
	public ResponseEntity<Projet> getunationplanatprojet(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok().body( risqueservice.getPlan_Attunation(id).getR().getP());
	}
	@GetMapping("/getplanaContingence/{id}")
	public ResponseEntity<?> getplan_contingence(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok().body( risqueservice.getPlan_Contingence(id));
	}
	@GetMapping("/getplanaContingenceprojet/{id}")
	public ResponseEntity<Projet> getplan_contingenceprojet(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok().body( risqueservice.getPlan_Contingence(id).getR().getP() );
	}
	@GetMapping("/getActionAttunation/{id}")
	public ResponseEntity<?>getActionAttenuation(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok().body( pla.getAll(id));
	}
	@GetMapping("/getActionContingence/{id}")
	public ResponseEntity<?> getActionContingence(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok().body( plc.getallactions(id));
	}
	@GetMapping("/getpa/{id}")
	public ResponseEntity<?> getpa(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok().body( pla.getByid(id));
	}
	@PostMapping("/upload1/{id}")
	public org.springframework.http.ResponseEntity.BodyBuilder uplaodImage(@PathVariable Long id,@RequestParam("imageFile") MultipartFile file) throws Exception {

		System.out.println("Original Image Byte Size - " + file.getBytes().length);
		ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
				compressBytes(file.getBytes()));
		this.userservice.getUserById(id).setImg(img);
		imageRepository.save(img);
		return ResponseEntity.status(HttpStatus.OK);
	}

	@GetMapping(path = { "/get122/{id}" })
	public ImageModel getImage(@PathVariable("id")Long  id) throws Exception {
		ImageModel retrievedImage= this.userservice.getUserById(id).getImg();
		//final Optional<ImageModel> retrievedImage = imageRepository.findByName(imageName);
		ImageModel img = new ImageModel(retrievedImage.getName(), retrievedImage.getType(),
				decompressBytes(retrievedImage.getPicByte()));
		return img;
	}

	// compress the image bytes before storing it in the database
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}

	// uncompress the image bytes before returning it to the angular application
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
	@PutMapping ("/updatee/{id}")
	public void update(@PathVariable Long id,@RequestBody User user) throws Exception {
		this. userservice.updatedetails(id, user);
	}
	@GetMapping ("/test")
	public void update3(){
		Long b=(long) 3;
		System.out.println ( this.repoo.testfindrisque(b));
	}
	@PostMapping("/SaveCategorie")
	public void saveCategorie()  {
		ca.saveAll();


	}
	@GetMapping ("/getgategorie")
	public List<CategorieRisque> getgategorie(){
		return ca.getAllCategorie();
	}


	@PostMapping("/SaveStratigie")
	public void SaveStratigie () {
		st.saveAllStratigie();


	}
	@GetMapping ("/getstratigie")
	public List< Strategie> getstratigie(){
		return st.getAllStratigie();
	}
	@GetMapping ("/ge/{id}")
	public ResponseEntity<List<?>>C(@PathVariable Long id) throws Exception{
		return ResponseEntity.ok().body(repoo.tes(id));

	}

		
	
	
	@Data
	class Roletouserform {
		private String username ;
		private String rolename ;
		
	}

}

package gft.API.PartidoPoliticoAPI;

import gft.API.PartidoPoliticoAPI.entities.*;
import gft.API.PartidoPoliticoAPI.services.*;
import gft.API.PartidoPoliticoAPI.uploadImagens.FileUploadManager;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {

    private final PartidoService partidoService;
    private final PoliticoService politicoService;
    private final UsuarioService usuarioService;
    private final ProjetoLeiService projetoLeiService;
    private final ProcessoService processoService;

    public DbInit(PartidoService partidoService, PoliticoService politicoService, UsuarioService usuarioService, ProjetoLeiService projetoLeiService, ProcessoService processoService) {
        this.partidoService = partidoService;
        this.politicoService = politicoService;
        this.usuarioService = usuarioService;
        this.projetoLeiService = projetoLeiService;
        this.processoService = processoService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Populating database...");

        //Create usuarios
        Usuario admin = new Usuario(null, "Admin", new BCryptPasswordEncoder().encode("Gft2021"), new Perfil(1L,"ADMIN"));
        Usuario usuario = new Usuario(null, "User", new BCryptPasswordEncoder().encode("Gft2021"), new Perfil(2L,"USER"));
        usuarioService.salvarUsuario(admin);
        usuarioService.salvarUsuario(usuario);

        //Create Partidos
        System.out.println("Creating partidos...");
        Partido pt = new Partido(null, "Partido dos Trabalhadores", "PT", new ArrayList<>());
        Partido mdb = new Partido(null, "Movimento Democrático Brasileiro", "MDB", new ArrayList<>());
        partidoService.salvarPartido(pt);
        partidoService.salvarPartido(mdb);
        System.out.println("Partidos created!");



        //Load imagens
        System.out.println("Loding images to C:/PartidoPoliticoAPI...");
        BufferedImage im1 = ImageIO.read(new File("imagens/default.png"));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(im1,"png", os);
        InputStream fis = new ByteArrayInputStream(os.toByteArray());
        MultipartFile multipartFile = new MockMultipartFile("imagem",
                "default.png", "image/png", IOUtils.toByteArray(fis));

        String pastaDeFotos = "C:/PartidoPoliticoAPI/politicos-fotos/";
        FileUploadManager.saveFile(pastaDeFotos, "default.png", multipartFile);

        BufferedImage im2 = ImageIO.read(new File("imagens/politicos/deputado/1.png"));
        ByteArrayOutputStream os2 = new ByteArrayOutputStream();
        ImageIO.write(im2,"png", os2);
        InputStream fis2 = new ByteArrayInputStream(os2.toByteArray());
        MultipartFile multipartFile2 = new MockMultipartFile("imagem",
                "1.png", "image/png", IOUtils.toByteArray(fis2));

        String pastaDeFotos2 = "C:/PartidoPoliticoAPI/politicos-fotos/1";
        FileUploadManager.saveFile(pastaDeFotos2, "1.png", multipartFile2);

        BufferedImage im3 = ImageIO.read(new File("imagens/politicos/governador/3.png"));
        ByteArrayOutputStream os3 = new ByteArrayOutputStream();
        ImageIO.write(im3,"png", os3);
        InputStream fis3 = new ByteArrayInputStream(os3.toByteArray());
        MultipartFile multipartFile3 = new MockMultipartFile("imagem",
                "3.png", "image/png", IOUtils.toByteArray(fis3));

        String pastaDeFotos3 = "C:/PartidoPoliticoAPI/politicos-fotos/2";
        FileUploadManager.saveFile(pastaDeFotos3, "3.png", multipartFile3);

        BufferedImage im4 = ImageIO.read(new File("imagens/politicos/ministro/1.png"));
        ByteArrayOutputStream os4 = new ByteArrayOutputStream();
        ImageIO.write(im4,"png", os4);
        InputStream fis4 = new ByteArrayInputStream(os4.toByteArray());
        MultipartFile multipartFile4 = new MockMultipartFile("imagem",
                "1.png", "image/png", IOUtils.toByteArray(fis4));

        String pastaDeFotos4 = "C:/PartidoPoliticoAPI/politicos-fotos/3";
        FileUploadManager.saveFile(pastaDeFotos4, "1.png", multipartFile4);

        BufferedImage im5 = ImageIO.read(new File("imagens/politicos/prefeito/2.png"));
        ByteArrayOutputStream os5 = new ByteArrayOutputStream();
        ImageIO.write(im5,"png", os5);
        InputStream fis5 = new ByteArrayInputStream(os5.toByteArray());
        MultipartFile multipartFile5 = new MockMultipartFile("imagem",
                "2.png", "image/png", IOUtils.toByteArray(fis5));

        String pastaDeFotos5 = "C:/PartidoPoliticoAPI/politicos-fotos/4";
        FileUploadManager.saveFile(pastaDeFotos5, "2.png", multipartFile5);

        BufferedImage im6 = ImageIO.read(new File("imagens/politicos/presidente/2.png"));
        ByteArrayOutputStream os6 = new ByteArrayOutputStream();
        ImageIO.write(im6,"png", os6);
        InputStream fis6 = new ByteArrayInputStream(os6.toByteArray());
        MultipartFile multipartFile6 = new MockMultipartFile("imagem",
                "2.png", "image/png", IOUtils.toByteArray(fis6));

        String pastaDeFotos6 = "C:/PartidoPoliticoAPI/politicos-fotos/5";
        FileUploadManager.saveFile(pastaDeFotos6, "2.png", multipartFile6);

        BufferedImage im7 = ImageIO.read(new File("imagens/politicos/senador/3.png"));
        ByteArrayOutputStream os7 = new ByteArrayOutputStream();
        ImageIO.write(im7,"png", os7);
        InputStream fis7 = new ByteArrayInputStream(os7.toByteArray());
                MultipartFile multipartFile7 = new MockMultipartFile("imagem",
                "3.png", "image/png", IOUtils.toByteArray(fis7));

        String pastaDeFotos7 = "C:/PartidoPoliticoAPI/politicos-fotos/6";
        FileUploadManager.saveFile(pastaDeFotos7, "3.png", multipartFile7);

        BufferedImage im8 = ImageIO.read(new File("imagens/politicos/vereador/2.png"));
        ByteArrayOutputStream os8 = new ByteArrayOutputStream();
        ImageIO.write(im8,"png", os8);
        InputStream fis8 = new ByteArrayInputStream(os8.toByteArray());
        MultipartFile multipartFile8 = new MockMultipartFile("imagem",
                "2.png", "image/png", IOUtils.toByteArray(fis8));

        String pastaDeFotos8 = "C:/PartidoPoliticoAPI/politicos-fotos/7";
        FileUploadManager.saveFile(pastaDeFotos8, "2.png", multipartFile8);
        System.out.println("Images loaded!");

        //Create politicos
        System.out.println("Creating politicos...");
        Endereco enderecoPadrao = new Endereco("Rua A", "001", "", "70040-010");
        List<ProjetoLei> projetoLeis = new ArrayList<>();
        List<Processo> processos = new ArrayList<>();

        Deputado p1 = new Deputado(null, "Livia Cunha Lima", "964.661.910-01", enderecoPadrao, "99096565", pt, "1.png",
                projetoLeis , processos, false);
        Governador p2 = new Governador(null, "Luis Rodrigues Melo", "935.473.910-59", enderecoPadrao, "99096565", mdb, "3.png",
                projetoLeis, processos);
        Ministro p3 = new Ministro(null, "Emilly Carvalho Santos", "684.444.470-21", enderecoPadrao, "99096565", pt, "1.png",
                projetoLeis, processos);
        Prefeito p4 = new Prefeito(null, "Antônio Rocha Martins", "151.806.690-90", enderecoPadrao, "99096565", mdb, "2.png",
                projetoLeis, processos);
        Presidente p5 = new Presidente(null, "Jair Bolsonaro", "209.137.630-20", enderecoPadrao, "99096565", mdb, "2.png",
                projetoLeis, processos);
        Senador p6 = new Senador(null, "Murilo Melo Santos", "873.485.650-18", enderecoPadrao, "99096565", pt, "3.png",
                projetoLeis, processos);
        Vereador p7 = new Vereador(null, "Erick Lima Pinto", "317.407.120-80", enderecoPadrao, "99096565", mdb, "2.png",
                projetoLeis, processos);
        politicoService.salvarPolitico(p1);
        politicoService.salvarPolitico(p2);
        politicoService.salvarPolitico(p3);
        politicoService.salvarPolitico(p4);
        politicoService.salvarPolitico(p5);
        politicoService.salvarPolitico(p6);
        politicoService.salvarPolitico(p7);
        System.out.println("Politicos created!");

        System.out.println("Creating projetos...");
        ProjetoLei pj1 = new ProjetoLei(null,p4, "Construção de Escola", "Nova escola para o bairro Santa Maria");
        ProjetoLei pj2 = new ProjetoLei(null,p7, "Reparo de Rua", "Reparos na avenida principal da cidade");
        ProjetoLei pj3 = new ProjetoLei(null,p5, "Reforma trabalhista", "Mudanças na idade para aposentadoria");
        projetoLeiService.salvarProjetoLei(pj1);
        projetoLeiService.salvarProjetoLei(pj2);
        projetoLeiService.salvarProjetoLei(pj3);
        System.out.println("Projetos created!");

        System.out.println("Creating processos...");
        Processo pr1 = new Processo(null, p3, true, "Desvio de fundos");
        Processo pr2 = new Processo(null, p2, true, "Desvio de fundos");
        Processo pr3 = new Processo(null, p1, true, "Desvio de fundos");
        processoService.salvarProcesso(pr1);
        processoService.salvarProcesso(pr2);
        processoService.salvarProcesso(pr3);
        System.out.println("Processos created!");

        System.out.println("Database populated!");
    }
}

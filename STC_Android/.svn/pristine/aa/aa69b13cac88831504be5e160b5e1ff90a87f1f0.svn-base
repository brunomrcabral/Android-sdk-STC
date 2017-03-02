package com.example.hugo.stc_android.Model.Utils;

/**
 * Created by Hugo on 21-08-2015.
 */

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.bluetooth.BluetoothDevice;
import android.os.Environment;
import android.util.Log;

import com.woosim.bt.WoosimPrinter;

public class WoosimPlugin {

    private static final String CONECTA = "conecta";
    private static final String IMPRIME = "imprime";
    private static final String DESCONECTA = "desconecta";

    private static String ACTION_UUID = "";
    private static String EXTRA_UUID = "";

    private final static String EUC_KR = "ISO8859-15";

    private WoosimPrinter woosim;


    public WoosimPlugin() {

        try {

            Field actionUUID = BluetoothDevice.class.getDeclaredField ( "ACTION_UUID" );
            WoosimPlugin.ACTION_UUID = ( String ) actionUUID.get ( null );

            Field extraUUID = BluetoothDevice.class.getDeclaredField ( "EXTRA_UUID" );
            WoosimPlugin.EXTRA_UUID = ( String ) extraUUID.get ( null );

        } catch ( Exception e ) {

            System.out.println ( "Erro Woosim Plugin: " + e.getMessage ( ) );

        };
    };


    public String formatalinhas(String Nome, String Dado, String Dados) {

        String linha = Nome + ":";

        if (Nome.equals("Agente")){

            String linhas = Dado + " - " + Dados;
            int maximo = 42 - (linha.length() + linhas.length());
            for (int count = 0; count < maximo; count++) {
                linha += " ";
            }
            linha += linhas + "\r\n";

        } else if (Nome.equals("Coima")){

            String coima =  "de " + Dado + "€ a " + Dados+ "€";
            int maximo = 42 - (linha.length() + coima.length());
            for (int count = 0; count < maximo; count++) {
                linha += " ";
            }
            linha += coima + "\r\n";

        } else {

            int maximo = 42 - (linha.length() + Dado.length());

            for (int count = 0; count < maximo; count++) {
                linha += " ";
            }

            linha += Dado + "\r\n";
        }

        return linha;
    };

    public String formataTurno(String id, String matricula, String data, String infraccao) {

        String linha = id;

        // Calcula espaços para inserir a matricula.
        int NumeroEspaços = ( 10 - linha.length ( ) );

        for ( int Contador = 0; Contador <= NumeroEspaços; Contador ++ ) {

            linha = linha + " ";

        };

        linha = linha + matricula;

        // Calcula espaços para inserir a infracção.
        NumeroEspaços = ( 24 - linha.length ( ) );

        for ( int Contador = 0; Contador <= NumeroEspaços; Contador ++ ) {

            linha = linha + " ";

        };

        linha = linha + infraccao;

        // Calcula espaços para inserir a data.
        NumeroEspaços = ( 36 - linha.length ( ) );

        for ( int Contador = 0; Contador <= NumeroEspaços; Contador ++ ) {

            linha = linha + " ";

        };

        char caracter = data.charAt ( data.length ( ) - 5 );

        if ( Character.toString ( caracter ) == " " ) {

            data = data.substring ( data.length ( ) - 4, data.length ( ) );

        } else {

            data = data.substring ( data.length ( ) - 5, data.length ( ) );
        }

        linha = linha + data;

        return linha;
    }

    public String getId() {
        String id = UUID.randomUUID().toString();
        //String id = android.provider.Settings.System.getString(cordova.getActivity().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        return id;
    }

    /*public PluginResult RespostaImpressora ( int Mensagem ) {

        System.out.println ( "Recebe pedido para verificar a resposta enviada pela impressora." );

        PluginResult pluginResult = null;

        if ( Mensagem == 1 ) {

            pluginResult = new PluginResult ( PluginResult.Status.OK );

            System.out.println ( "Impressora Resposta: " + Mensagem );

        } else if ( Mensagem == -2 ) {

            pluginResult = new PluginResult ( PluginResult.Status.ERROR, Mensagem );

            System.out.println ( "Impressora Resposta: " + Mensagem );

        } else if ( Mensagem == -5 ) {

            pluginResult = new PluginResult ( PluginResult.Status.ERROR, Mensagem );

            System.out.println ( "Impressora Resposta: " + Mensagem );

        } else if ( Mensagem == -6 ) {

            pluginResult = new PluginResult ( PluginResult.Status.ERROR, Mensagem );

            System.out.println ( "Impressora Resposta: " + Mensagem );

        } else if ( Mensagem== -8 ) {

            pluginResult = new PluginResult ( PluginResult.Status.ERROR, Mensagem );

            System.out.println ( "Impressora Resposta: " + Mensagem );

        } else {

            pluginResult = new PluginResult ( PluginResult.Status.OK, Mensagem );

            System.out.println ( "Impressora Resposta: " + Mensagem );

        };

        return pluginResult;
    };*/

    //@Override
    //public boolean execute ( String action, JSONArray arg, CallbackContext callbackContext ) throws JSONException {
    public boolean execute ( String action, JSONArray arg) throws JSONException {

        //PluginResult pluginResult = null;

        if ( CONECTA.equals ( action ) ) {

            System.out.println ( "É executada uma acção de conecção com a impressora." );

            woosim = new WoosimPrinter ( );

            System.out.println ( "É inicializado o plug-in da impressora." );

            boolean inSecure = true;

            //String version = android.os.Build.VERSION.RELEASE;
            //int osVersion = Integer.parseInt ( version.substring(0, 1) + version.substring ( 2, 3 ) + version.substring ( 4, 5 ) ); -- Linha encontra-se com bug pois fazia parce a uma região em que não exisitam caracteres.
            //System.out.println ( "Versão: " + version );
            //if ( osVersion >= 236 ) {
            //inSecure = true;
            //System.out.println ( "Segurança Activada" );
            //} else {
            //inSecure = false;
            //System.out.println ( "Segurança Desactivada" );
            //};

            //callbackContext.sendPluginResult ( RespostaImpressora ( woosim.BTConnection ( inSecure, arg.getString(0), false ) ) );

        } else if ( IMPRIME.equals ( action ) ) {

            System.out.println ( "É executada uma acção de Impressão." );

            JSONObject json = new JSONObject ( arg.getString ( 0 ) );

            System.out.println ( "Json: " + json );

            byte [ ] init 				= { 0x1B, '@' };

            woosim.controlCommand ( init, init.length );

            byte [ ] alinhadocentro 	= { 0x1B, 0x61, 0x1 };
            byte [ ] alinhadoesquerda 	= { 0x1B, 0x61, 0x0 };
            byte [ ] ff 				= { 0x0c };
            byte [ ] lf 				= { 0x0a };

            byte [ ] tipocaracter;
            if(json.getString("modeloImpressora") != null && json.getString("modeloImpressora").equals("2")){
                tipocaracter = new byte[]{ 0x1B, 0x74, 0xd };
            }else{
                tipocaracter = new byte[]{ 0x1B, 0x74, 0x4 };
            }

            woosim.controlCommand ( tipocaracter, tipocaracter.length );

            String Caminho = Environment.getExternalStorageDirectory ( ) .getAbsolutePath ( );


            System.out.println ( "Caminho para o logotipo: " + json.getString("logo") );


            if ( json.getString("logo") != "" ) {

                /*try {

                    RespostaImpressora ( woosim.printBitmap ( Caminho + json.getString("logo") ) );

                } catch ( IOException e ) {

                    System.out.println ( "Erro Imprimir Logo: " + e.getMessage ( ) );

                };*/

            };

            woosim.controlCommand(ff, 1);

            // ---------------------------------------------------------- //

            if ( json.getString ( "tiporegisto" ).equals ( "AI" ) ) {

                Log.i("Tipo Registo", "entra AI");

                woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                woosim.saveSpool(EUC_KR,"\r\nDenúncia\r\n\r\n", 0x11, true);

                woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                woosim.saveSpool(EUC_KR,formatalinhas("Número",json.getString("numero"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Agente",json.getString("fiscal"),json.getString("nomefiscal")), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Data & Hora",json.getString("datahora"),""), 0, false);

                woosim.saveSpool(EUC_KR,"------------------------------------------\r\n", 0, false);
                woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                woosim.saveSpool(EUC_KR,"Local da Infracção\r\n", 0, false);
                woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                woosim.saveSpool(EUC_KR,"------------------------------------------\r\n", 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Zona",json.getString("zona"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Rua",json.getString("rua"),""), 0, false);

                woosim.saveSpool(EUC_KR,"\r\n------------------------------------------\r\n", 0, false);
                woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                woosim.saveSpool(EUC_KR,"Tipo de Infracção\r\n", 0, false);
                woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                woosim.saveSpool(EUC_KR,"------------------------------------------\r\n", 0, false);
                woosim.saveSpool(EUC_KR,json.getString("infracao") + "\r\n", 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Legislação",json.getString("legisla"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Coima",json.getString("coimaminima"),json.getString("coimamaxima")), 0, false);

                woosim.saveSpool(EUC_KR,"\r\n------------------------------------------\r\n", 0, false);
                woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                woosim.saveSpool(EUC_KR,"Dados do Veículo\r\n", 0, false);
                woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                woosim.saveSpool(EUC_KR,"------------------------------------------\r\n", 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Pais",json.getString("pais"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Matrícula",json.getString("matricula"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Categoria",json.getString("categoria"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Tipo Veículo",json.getString("tipoveiculo"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Marca",json.getString("marcas"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Cor",json.getString("cores"),""), 0, false);

                if ( json.getString("imprimeproprietario").equals("1") ) {

                    if (json.getString("tipoproprietario") != ""){

                        woosim.saveSpool(EUC_KR,"\r\n------------------------------------------\r\n", 0, false);
                        woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                        woosim.saveSpool(EUC_KR,"Dados do Proprietário\r\n", 0, false);
                        woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                        woosim.saveSpool(EUC_KR,"------------------------------------------\r\n", 0, false);
                        woosim.saveSpool(EUC_KR,formatalinhas("Tipo",json.getString("tipoproprietario"),""), 0, false);
                        woosim.saveSpool(EUC_KR,formatalinhas("Nome",json.getString("nome_proprietario"),""), 0, false);
                        woosim.saveSpool(EUC_KR,formatalinhas("Morada",json.getString("morada_proprietario"),""), 0, false);

                    }

                    woosim.saveSpool(EUC_KR,"\r\n------------------------------------------\r\n", 0, false);
                    woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                    woosim.saveSpool(EUC_KR,"Dados do Infractor\r\n", 0, false);
                    woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                    woosim.saveSpool(EUC_KR,"------------------------------------------\r\n", 0, false);
                    woosim.saveSpool(EUC_KR,formatalinhas("Nome",json.getString("nome"),""), 0, false);
                    woosim.saveSpool(EUC_KR,formatalinhas("Morada",json.getString("morada"),""), 0, false);
                    woosim.saveSpool(EUC_KR,formatalinhas("Data Nascimento",json.getString("datanascimento"),""), 0, false);

                    woosim.controlCommand(ff, 1);

                    woosim.saveSpool(EUC_KR,formatalinhas("Identificação",json.getString("tipoidentificacao"),""), 0, false);
                    woosim.saveSpool(EUC_KR,formatalinhas("Número",json.getString("numeroidentificacao"),""), 0, false);
                    woosim.saveSpool(EUC_KR,formatalinhas("Data",json.getString("dataidentificacao"),""), 0, false);
                    woosim.saveSpool(EUC_KR,formatalinhas("Entidade",json.getString("entidadeidentificacao"),""), 0, false);
                    woosim.saveSpool(EUC_KR,formatalinhas("Nº Contribuinte",json.getString("numerocontribuinte"),""), 0, false);

                    woosim.controlCommand(ff, 1);

                    woosim.saveSpool(EUC_KR,formatalinhas("Carta/Licença",json.getString("tipodocumentolicenca"),""), 0, false);
                    woosim.saveSpool(EUC_KR,formatalinhas("Número",json.getString("numerolicenca"),""), 0, false);
                    woosim.saveSpool(EUC_KR,formatalinhas("Data",json.getString("datalicenca"),""), 0, false);
                    woosim.saveSpool(EUC_KR,formatalinhas("Entidade",json.getString("entidadelicenca"),""), 0, false);
                };

                woosim.controlCommand(lf, 1);
                woosim.controlCommand(lf, 1);

                woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                woosim.saveSpool(EUC_KR,json.get("rodape").toString() + "\r\n", 0, false);

            } else if (json.getString("tiporegisto").equals("ADV")){

                Log.i("Tipo Registo", "entrou ADV");

                woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                woosim.saveSpool(EUC_KR,"\r\nAdvertência\r\n\r\n", 0x11, false);
                woosim.saveSpool(EUC_KR,"Recomendamos-lhe que cumpra o Código da Estrada (CE) e os Regulamentos Municipais.\r\nNão prejudique os direitos dos outros.\r\n\r\n" , 0, false);

                woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                woosim.saveSpool(EUC_KR,formatalinhas("Número",json.getString("numero"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Data & Hora",json.getString("datahora"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Zona",json.getString("zona"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Rua",json.getString("rua"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Matrícula",json.getString("matricula"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Marca",json.getString("marcas"),""), 0, false);

                if ( json.getString("imprimeproprietario").equals("1") ) {

                    if (json.getString("tipoproprietario") != ""){

                        woosim.saveSpool(EUC_KR,"\r\n------------------------------------------\r\n", 0, false);
                        woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                        woosim.saveSpool(EUC_KR,"Dados do Proprietário\r\n", 0, false);
                        woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                        woosim.saveSpool(EUC_KR,"------------------------------------------\r\n", 0, false);
                        woosim.saveSpool(EUC_KR,formatalinhas("Tipo",json.getString("tipoproprietario"),""), 0, false);
                        woosim.saveSpool(EUC_KR,formatalinhas("Nome",json.getString("nome_proprietario"),""), 0, false);
                        woosim.saveSpool(EUC_KR,formatalinhas("Morada",json.getString("morada_proprietario"),""), 0, false);

                    };

                    if ( json.getString("nome") != "" ) {

                        woosim.saveSpool(EUC_KR,"\r\n------------------------------------------\r\n", 0, false);
                        woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                        woosim.saveSpool(EUC_KR,"Dados do Infractor\r\n", 0, false);
                        woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                        woosim.saveSpool(EUC_KR,"------------------------------------------\r\n", 0, false);
                        woosim.saveSpool(EUC_KR,formatalinhas("Nome",json.getString("nome"),""), 0, false);
                        woosim.saveSpool(EUC_KR,formatalinhas("Morada",json.getString("morada"),""), 0, false);
                        woosim.saveSpool(EUC_KR,formatalinhas("Data Nascimento",json.getString("datanascimento"),""), 0, false);

                        woosim.controlCommand(ff, 1);

                        woosim.saveSpool(EUC_KR,formatalinhas("Identificação",json.getString("tipoidentificacao"),""), 0, false);
                        woosim.saveSpool(EUC_KR,formatalinhas("Número",json.getString("numeroidentificacao"),""), 0, false);
                        woosim.saveSpool(EUC_KR,formatalinhas("Data",json.getString("dataidentificacao"),""), 0, false);
                        woosim.saveSpool(EUC_KR,formatalinhas("Entidade",json.getString("entidadeidentificacao"),""), 0, false);
                        woosim.saveSpool(EUC_KR,formatalinhas("Nº Contribuinte",json.getString("numerocontribuinte"),""), 0, false);

                        woosim.controlCommand(ff, 1);

                        woosim.saveSpool(EUC_KR,formatalinhas("Carta/Licença",json.getString("tipodocumentolicenca"),""), 0, false);
                        woosim.saveSpool(EUC_KR,formatalinhas("Número",json.getString("numerolicenca"),""), 0, false);
                        woosim.saveSpool(EUC_KR,formatalinhas("Data",json.getString("datalicenca"),""), 0, false);
                        woosim.saveSpool(EUC_KR,formatalinhas("Entidade",json.getString("entidadelicenca"),""), 0, false);

                    };

                };

                woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                woosim.saveSpool(EUC_KR,"\r\n\r\n Respeitar o CE é um Dever Cívico!\r\n", 0x11, false);
                woosim.saveSpool(EUC_KR,"\r\nEvite a aplicação de sanções", 0, false);

                woosim.controlCommand(lf, 1);

            } else if (json.getString("tiporegisto").equals("TMX")){

                Log.i("Tipo Registo", "entrou ADP");

                String estacionamentovalor = json.getString("estacionamentovalor") + "€";
                String horamaxima          = json.getString("horamaxima");
                String datamaxima          = json.getString("datamaxima");

                woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                woosim.saveSpool(EUC_KR,"\r\nPagamento de Ocupação Indevida\r\n\r\n", 0x11, false);

                woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                woosim.saveSpool(EUC_KR,formatalinhas("Número",json.getString("numero"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Agente",json.getString("fiscal"),json.getString("nomefiscal")), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Data & Hora",json.getString("datahora"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Matrícula",json.getString("matricula"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Marca",json.getString("marcas"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Zona",json.getString("zona"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Rua",json.getString("rua"),""), 0, false);

                woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                woosim.saveSpool(EUC_KR,"\r\nSolicita-se o pagamento de taxa de estaci-\r\nonamento no valor de " + estacionamentovalor + " nos termos do \r\nart. º 34º, nº5 com a sanção prevista no \r\nart. º 47 do Regulamento Geral de Estacio-\r\nnamento e Circulação nas Zonas de Estacio-\r\nnamento de Duração Limitada.\r\n\r\n" , 0, false);
                woosim.saveSpool(EUC_KR,"Esta importância deverá ser paga até às \r\n" + horamaxima +" horas do dia " + datamaxima + ", conforme \r\nindicações de pagamento MB ou através do \r\nnosso posto de atendimento na nossa sede.\r\n\r\n" , 0, false);

                woosim.saveSpool(EUC_KR,"------------------------------------------\r\n", 0, false);
                woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                woosim.saveSpool(EUC_KR,"PAGAMENTO POR MULTIBANCO\r\n", 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Entidade",json.getString("entidade"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Referência",json.getString("referencia"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Valor a pagar",estacionamentovalor,""), 0, false);
                woosim.saveSpool(EUC_KR,"------------------------------------------\r\n", 0, false);

                //woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                //woosim.saveSpool(EUC_KR,"\r\nEm caso de não pagamento será aplicado\r\n", 0, false);
                //woosim.saveSpool(EUC_KR,formatalinhas("Infracção",json.getString("infracao"),""), 0, false);
                //woosim.saveSpool(EUC_KR,formatalinhas("Coima",json.getString("coimaminima"),json.getString("coimamaxima")), 0, false);

                woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                woosim.saveSpool(EUC_KR,"\r\nO não pagamento dará origem a um processo de contra-ordenação por violação da legis-\r\nlação aplicável, art. º 71 do Código da \r\nEstrada.\r\n", 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Coima",json.getString("coimaminima"),json.getString("coimamaxima")), 0, false);

                woosim.controlCommand(lf, 1);

            }

            if(json.getString("tiporegisto").equals("2")){



            }

            if(json.getString("tiporegisto").equals("3")){

                int nadv   = 0;
                int nai    = 0;
                int ntmx   = 0;
                int nautos = 0;
                int nbloq  = 0;
                int nreb   = 0;
                int nvfv   = 0;

                woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                woosim.saveSpool(EUC_KR,"\r\nFecho de Turno\r\n\r\n", 0x11, true);

                JSONArray fiscal = json.getJSONArray("fiscal");

                woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                woosim.saveSpool(EUC_KR,formatalinhas("PDA",          fiscal.getJSONObject(0).getString("pda"),""),    0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Agente",       fiscal.getJSONObject(0).getString("idfiscal"),   fiscal.getJSONObject(0).getString("nomefiscal")), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Início Turno", fiscal.getJSONObject(0).getString("inicio"),""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Fim de Turno", fiscal.getJSONObject(0).getString("fim"),""),    0, false);

                if (json.getString("nai").equals("1")){

                    JSONArray contraordenacao = json.getJSONArray("contraordenacao");
                    nai = contraordenacao.length();

                    woosim.saveSpool(EUC_KR,"------------------------------------------\r\n", 0, false);
                    woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                    woosim.saveSpool(EUC_KR,"Denúncias\r\n", 0, false);
                    woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                    woosim.saveSpool(EUC_KR,"------------------------------------------\r\n", 0, false);
                    woosim.saveSpool(EUC_KR,"Número     Matrícula     Infracção    Hora\r\n", 0, false);

                    for (int i = 0; i < contraordenacao.length(); i++) {
                        woosim.saveSpool(EUC_KR,formataTurno(contraordenacao.getJSONObject(i).getString("id"),contraordenacao.getJSONObject(i).getString("matricula"),contraordenacao.getJSONObject(i).getString("data"),contraordenacao.getJSONObject(i).getString("infraccao")), 0, false);
                    }
                }

                if (json.getString("nadv").equals("1")){

                    JSONArray advertencias = json.getJSONArray("advertencias");
                    nadv = advertencias.length();

                    woosim.saveSpool(EUC_KR,"\r\n------------------------------------------\r\n", 0, false);
                    woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                    woosim.saveSpool(EUC_KR,"Advertências\r\n", 0, false);
                    woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                    woosim.saveSpool(EUC_KR,"------------------------------------------\r\n", 0, false);
                    woosim.saveSpool(EUC_KR,"Número     Matrícula     Infracção    Hora\r\n", 0, false);

                    for (int i = 0; i < advertencias.length(); i++) {
                        woosim.saveSpool(EUC_KR,formataTurno(advertencias.getJSONObject(i).getString("id"),advertencias.getJSONObject(i).getString("matricula"),advertencias.getJSONObject(i).getString("data"),advertencias.getJSONObject(i).getString("infraccao")), 0, false);
                    }
                }

                if (json.getString("ntmx").equals("1")){

                    JSONArray avisopagamento = json.getJSONArray("avisopagamento");
                    ntmx                     = avisopagamento.length();

                    woosim.saveSpool(EUC_KR,"\r\n------------------------------------------\r\n", 0, false);
                    woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                    woosim.saveSpool(EUC_KR,"Avisos Pagamento Ocup. Indevida\r\n", 0, false);
                    woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                    woosim.saveSpool(EUC_KR,"------------------------------------------\r\n", 0, false);
                    woosim.saveSpool(EUC_KR,"Número     Matrícula     Infracção    Hora\r\n", 0, false);

                    for (int i = 0; i < avisopagamento.length(); i++) {
                        woosim.saveSpool(EUC_KR,formataTurno(avisopagamento.getJSONObject(i).getString("id"),avisopagamento.getJSONObject(i).getString("matricula"),avisopagamento.getJSONObject(i).getString("data"),avisopagamento.getJSONObject(i).getString("infraccao")), 0, false);
                    }
                }

                woosim.saveSpool(EUC_KR,"\r\n------------------------------------------\r\n", 0, false);
                woosim.controlCommand(alinhadocentro, alinhadocentro.length);
                woosim.saveSpool(EUC_KR,"Total de Documentos Emitidos por Tipo\r\n", 0, false);
                woosim.controlCommand(alinhadoesquerda, alinhadoesquerda.length);
                woosim.saveSpool(EUC_KR,"------------------------------------------\r\n", 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Denúncias",                       Integer.toString(nai),    ""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Advertências",                    Integer.toString(nadv),   ""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Avisos Pagamento Ocup. Indevida", Integer.toString(ntmx),   ""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Autos",                           Integer.toString(nautos), ""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Bloqueios",                       Integer.toString(nbloq),  ""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Reboques",                        Integer.toString(nreb),   ""), 0, false);
                woosim.saveSpool(EUC_KR,formatalinhas("Veículo em Fim de Vida",          Integer.toString(nvfv),   ""), 0, false);

                woosim.controlCommand(lf, 1);
                woosim.controlCommand(lf, 1);
            }

            woosim.controlCommand(alinhadocentro, alinhadocentro.length);
            woosim.saveSpool(EUC_KR,"\r\nProcessado por STC - Software de Trânsito e Contra-Ordenações. ANO Lda\r\n", 0, false);

            // ---------------------------------------------------------- //

            woosim.controlCommand(lf, 1);
            woosim.controlCommand(lf, 1);
            woosim.controlCommand(lf, 1);

            //callbackContext.sendPluginResult ( RespostaImpressora ( woosim.printSpool ( true ) ) );


        } else if ( DESCONECTA.equals ( action ) ) {

            System.out.println ( "Realizado um pedido para desconectar a impressora do dispositivo." );

            //callbackContext.sendPluginResult ( RespostaImpressora (  woosim.BTDisConnection ( ) ) );

        };

        return true;
    };

};

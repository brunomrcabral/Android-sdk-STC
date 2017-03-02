package com.example.hugo.stc_android.Model.Utils;

import android.database.Cursor;
import android.util.Log;

import com.example.hugo.stc_android.Model.Persistence.ModelCartaoResidente;
import com.example.hugo.stc_android.Model.Persistence.ModelCategorias;
import com.example.hugo.stc_android.Model.Persistence.ModelControloSincronizacao;
import com.example.hugo.stc_android.Model.Persistence.ModelCores;
import com.example.hugo.stc_android.Model.Persistence.ModelDanos;
import com.example.hugo.stc_android.Model.Persistence.ModelEstados;
import com.example.hugo.stc_android.Model.Persistence.ModelFiscais;
import com.example.hugo.stc_android.Model.Persistence.ModelLegislacao;
import com.example.hugo.stc_android.Model.Persistence.ModelMarcas;
import com.example.hugo.stc_android.Model.Persistence.ModelMotivos;
import com.example.hugo.stc_android.Model.Persistence.ModelPDAs;
import com.example.hugo.stc_android.Model.Persistence.ModelPaises;
import com.example.hugo.stc_android.Model.Persistence.ModelParametrosPDA;
import com.example.hugo.stc_android.Model.Persistence.ModelParques;
import com.example.hugo.stc_android.Model.Persistence.ModelReboques;
import com.example.hugo.stc_android.Model.Persistence.ModelRuas;
import com.example.hugo.stc_android.Model.Persistence.ModelTarifasMaximas;
import com.example.hugo.stc_android.Model.Persistence.ModelTextoRodape;
import com.example.hugo.stc_android.Model.Persistence.ModelTipoAccao1;
import com.example.hugo.stc_android.Model.Persistence.ModelTipoAccao2;
import com.example.hugo.stc_android.Model.Persistence.ModelTipoDocumentos;
import com.example.hugo.stc_android.Model.Persistence.ModelTipoFigura;
import com.example.hugo.stc_android.Model.Persistence.ModelTipoInfraccoes;
import com.example.hugo.stc_android.Model.Persistence.ModelTipoOcorrencias;
import com.example.hugo.stc_android.Model.Persistence.ModelTipoOrdemServico;
import com.example.hugo.stc_android.Model.Persistence.ModelTipoVeiculos;
import com.example.hugo.stc_android.Model.Persistence.ModelTopMatriculas;
import com.example.hugo.stc_android.Model.Persistence.ModelViaComunicacoes;
import com.example.hugo.stc_android.Model.Persistence.ModelZonas;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Hugo
 */
public class WebserviceSoapClient {

    private static final String NAMESPACE = "http://tempuri.org/STCws/infobase";
    private static final String SOAP_ACTION = "http://tempuri.org/STCws/infobase/";
    private String SERVICE_URL;
    private int MAX_TIMEOUT;

    // Ligar variável em testes para ver pedido e resposta das operações com o WS
    private boolean DEBUG = true;

    //private static String SESSION_ID;

    public WebserviceSoapClient(String service_url, String timeout) {
        SERVICE_URL = service_url;
        MAX_TIMEOUT = Integer.parseInt(timeout);
    }

    private SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject request) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);
        return envelope;
    }

    private HttpTransportSE getHttpTransportSE() {
        HttpTransportSE ht = new HttpTransportSE(Proxy.NO_PROXY, SERVICE_URL, MAX_TIMEOUT);
        ht.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        ht.debug = DEBUG;
        return ht;
    }

    /*private List<HeaderProperty> getHeader() {
        List<HeaderProperty> header = new ArrayList<>();
        HeaderProperty headerPropertyObj = new HeaderProperty("cookie", WebserviceSoapClient.SESSION_ID);
        header.add(headerPropertyObj);
        return header;
    }*/

    private void testHttpResponse(HttpTransportSE ht) {
        Log.i("SOAP REQUEST:", "Request XML:\n" + ht.requestDump);
        Log.i("SOAP RESPONSE:", "Response XML:\n" + ht.responseDump);
    }

    public int validaTerminal(String terminal, String accessCode, int numberAttempts) {
        String metodoWs = "Valida_Pda";

        SoapObject request = new SoapObject(NAMESPACE, metodoWs);
        request.addProperty("i_pda", terminal);
        request.addProperty("i_pda_codval", accessCode);

        SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);

        HttpTransportSE ht = getHttpTransportSE();
        try {
            ht.call(SOAP_ACTION + metodoWs, envelope);
            if(DEBUG)
                testHttpResponse(ht);

            SoapPrimitive resultado = (SoapPrimitive)envelope.getResponse();
            if(resultado.toString().equals("1")) {
                return 0;
            } else {
                return 1;
            }
        } catch (Exception e) {
            if(numberAttempts != 0) {
                return validaTerminal(terminal, accessCode, numberAttempts - 1);
            } else {
                return 2;
            }
        }
    }

    public ResponseDataObject validaDadosTabelas(String terminal, String accessCode, int numberAttempts, DatabaseHelper mdbHelper) {
        ResponseDataObject response = new ResponseDataObject();

        String metodoWs = "Download_Informacao_Base";

        SoapObject request = new SoapObject(NAMESPACE, metodoWs);
        request.addProperty("i_pda", terminal);
        request.addProperty("i_pda_codval", accessCode);
        request.addProperty("i_cod","Download_Controlo_Sincronizacao");

        SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);

        HttpTransportSE ht = getHttpTransportSE();
        try {
            ht.call(SOAP_ACTION + metodoWs, envelope);
            if (DEBUG)
                testHttpResponse(ht);

            SoapObject soapResponse = (SoapObject) envelope.getResponse();

            // Faz o parse da resposta devolvendo a estrutura da tabela de controlo de datas
            ResponseObjectWS parsedResponse = parseResposta(soapResponse);
            if (parsedResponse == null) {
                response.setStatusCode(1);
                return response;
            }

            // Popula registos para objeto de retorno
            response.setRecords(parsedResponse.getRegistos());

            // Verifica se a tabela de controlo existe e em caso negativo, cria-a
            if (!mdbHelper.checkTableExists(parsedResponse.getTabela())) {
                if (!mdbHelper.executeSqlStatement(ModelControloSincronizacao.SQL_CREATE_QUERY)) {
                    response.setStatusCode(2);
                    return response;
                }
                response.setStatusCode(0);
                response.setResponseData(WebserviceUtils.getEstruturaCompleta());
                return response;
            } else {
                // Verifica se tabela esta preenchida
                int numTableRows = mdbHelper.getCountRecordsFromTable(parsedResponse.getTabela());
                if(numTableRows == 0) {
                    response.setStatusCode(0);
                    response.setResponseData(WebserviceUtils.getEstruturaCompleta());
                    return response;
                }

                // Por cada tipo de dados com data, verifica se esta atualizado
                for(HashMap<String,String> record : parsedResponse.getRegistos()) {
                    Cursor row = mdbHelper.getOrderedRecordsByFilter(
                            parsedResponse.getTabela(),
                            null,
                            ModelControloSincronizacao.ModelControloSincronizacaoSchema.COLUMN_TIPO + "=?",
                            new String[]{record.get("INF_COD")},
                            null);

                    if(row.moveToFirst()) {
                        if(record.get("INF_DATA") != null &&
                                !record.get("INF_DATA").equals(row.getString(row.getColumnIndex(ModelControloSincronizacao.ModelControloSincronizacaoSchema.COLUMN_DATA)))) {

                            switch(record.get("INF_COD")) {
                                case "BASE1":
                                    response.getResponseData().addAll(WebserviceUtils.getEstruturaBase());
                                    break;
                                case "RUAS":
                                    response.getResponseData().addAll(WebserviceUtils.getEstruturaRuas());
                                    break;
                                case "CARTAO_RESIDENTE":
                                    response.getResponseData().addAll(WebserviceUtils.getEstruturaCartaoResidente());
                                    break;
                                case "TOP_MATRICULA":
                                    response.getResponseData().addAll(WebserviceUtils.getEstruturaTopMatricula());
                                    break;
                            }
                        }
                    }
                    row.close();
                }
                response.setStatusCode(0);
                return response;
            }
        } catch (Exception e) {
            if (numberAttempts != 0) {
                return validaDadosTabelas(terminal, accessCode, numberAttempts - 1, mdbHelper);
            } else {
                response.setStatusCode(3);
                return response;
            }
        }
    }

    private ResponseObjectWS parseResposta(SoapObject response) {
        // Verifica se existe elemento diffgram que contem NewDataSet com os dados
        if(!response.hasProperty("diffgram"))
            return null;

        SoapObject rootElement = (SoapObject) response.getProperty("diffgram");
        if(!rootElement.hasProperty("NewDataSet"))
            return null;

        SoapObject newDataSetElement = (SoapObject) rootElement.getProperty("NewDataSet");
        if(newDataSetElement.getPropertyCount() == 0)
            return null;

        PropertyInfo tableNameElement = new PropertyInfo();
        newDataSetElement.getPropertyInfo(0, tableNameElement);

        ResponseObjectWS datatableStructure = new ResponseObjectWS();
        datatableStructure.setTabela((tableNameElement.getName().split("DOWNLOAD_")[1]).toLowerCase());
        datatableStructure.setNumeroRegistos(newDataSetElement.getPropertyCount());
        datatableStructure.setNumeroCampos(((SoapObject) newDataSetElement.getProperty(0)).getPropertyCount());

        Log.i("TABELA:", datatableStructure.getTabela());
        Log.i("NUM REGISTOS:", Integer.toString(datatableStructure.getNumeroRegistos()));
        Log.i("NUM CAMPOS:", Integer.toString(datatableStructure.getNumeroCampos()));

        // Constroi mapa com os valores dos campos para cada registo da resposta
        for(int record = 0; record < datatableStructure.getNumeroRegistos(); record++) {
            SoapObject recordElement = (SoapObject) newDataSetElement.getProperty(record);
            LinkedHashMap<String,String> recordMap = new LinkedHashMap<>();

            for(int column = 0; column < datatableStructure.getNumeroCampos(); column++) {
                PropertyInfo columnName = new PropertyInfo();
                // Se nao existir valor para o campo acrescenta vazio, evitando o erro de processamento
                try {
                    recordElement.getPropertyInfo(column, columnName);
                    recordMap.put(columnName.getName(), recordElement.getProperty(column).toString());
                } catch(Exception e) {
                    recordMap.put("","");
                }
            }
            datatableStructure.getRegistos().add(recordMap);
        }
        return datatableStructure;
    }

    // Recebe os dados do servidor
    public int recebeDados(String terminal, String accessCode, List<String> listToDownload, int numberAttempts, DatabaseHelper mdbHelper) {
        String metodoWs = "Download_Informacao_Base";

        for(String dataToDownload : listToDownload) {
            SoapObject request = new SoapObject(NAMESPACE, metodoWs);
            request.addProperty("i_pda", terminal);
            request.addProperty("i_pda_codval", accessCode);
            request.addProperty("i_cod", dataToDownload);

            SoapSerializationEnvelope envelope = getSoapSerializationEnvelope(request);

            HttpTransportSE ht = getHttpTransportSE();
            try {
                ht.call(SOAP_ACTION + metodoWs, envelope);
                if (DEBUG)
                    testHttpResponse(ht);

                SoapObject soapResponse = (SoapObject) envelope.getResponse();

                // Faz o parse da resposta e caso existam dados, atualiza a tabela
                ResponseObjectWS parsedResponse = parseResposta(soapResponse);
                if(parsedResponse != null && !parsedResponse.getRegistos().isEmpty()) {
                    int downloadResponse;
                    if(mdbHelper.checkTableExists(parsedResponse.getTabela())) {
                        // Remove a tabela e criando uma nova com os dados atualizados
                        downloadResponse = downloadDataAndPopulateTable(parsedResponse, mdbHelper, true);
                    } else {
                        // Cria nova tabela e popula com os dados
                        downloadResponse = downloadDataAndPopulateTable(parsedResponse, mdbHelper, false);
                    }

                    // Se resposta do descarregamento nao for sucesso, repete pedido ou retorna erro
                    if(downloadResponse != 0) {
                        if (numberAttempts != 0) {
                            return recebeDados(terminal, accessCode, listToDownload, numberAttempts - 1, mdbHelper);
                        } else {
                            return downloadResponse;
                        }
                    }
                }
            } catch (Exception e) {
                if (numberAttempts != 0) {
                    return recebeDados(terminal, accessCode, listToDownload, numberAttempts - 1, mdbHelper);
                } else {
                    return 5;
                }
            }
        }
        return 0;
    }

    private int downloadDataAndPopulateTable(ResponseObjectWS responseObj, DatabaseHelper mdbHelper, boolean tableExists) {
        String deleteTableQuery;
        String createTableQuery;
        List<String> insertRecordsQueries = new ArrayList<>();
        int idRecord = 1;

        switch (responseObj.getTabela()) {
            case ModelTipoInfraccoes.ModelTipoInfraccoesSchema.TABLE_NAME :
                deleteTableQuery = ModelTipoInfraccoes.SQL_DELETE_QUERY;
                createTableQuery = ModelTipoInfraccoes.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelTipoInfraccoes.sqlInsertQuery(WebserviceUtils.getListWithoutId(record)));
                }
                break;
            case ModelCategorias.ModelCategoriasSchema.TABLE_NAME :
                deleteTableQuery = ModelCategorias.SQL_DELETE_QUERY;
                createTableQuery = ModelCategorias.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelCategorias.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelCores.ModelCoresSchema.TABLE_NAME :
                deleteTableQuery = ModelCores.SQL_DELETE_QUERY;
                createTableQuery = ModelCores.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelCores.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelFiscais.ModelFiscaisSchema.TABLE_NAME :
                deleteTableQuery = ModelFiscais.SQL_DELETE_QUERY;
                createTableQuery = ModelFiscais.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelFiscais.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelMarcas.ModelMarcasSchema.TABLE_NAME :
                deleteTableQuery = ModelMarcas.SQL_DELETE_QUERY;
                createTableQuery = ModelMarcas.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelMarcas.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelPDAs.ModelPDAsSchema.TABLE_NAME :
                deleteTableQuery = ModelPDAs.SQL_DELETE_QUERY;
                createTableQuery = ModelPDAs.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelPDAs.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelDanos.ModelDanosSchema.TABLE_NAME :
                deleteTableQuery = ModelDanos.SQL_DELETE_QUERY;
                createTableQuery = ModelDanos.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelDanos.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelParques.ModelParquesSchema.TABLE_NAME :
                deleteTableQuery = ModelParques.SQL_DELETE_QUERY;
                createTableQuery = ModelParques.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelParques.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelViaComunicacoes.ModelViaComunicacoesSchema.TABLE_NAME :
                deleteTableQuery = ModelViaComunicacoes.SQL_DELETE_QUERY;
                createTableQuery = ModelViaComunicacoes.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelViaComunicacoes.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelPaises.ModelPaisesSchema.TABLE_NAME :
                deleteTableQuery = ModelPaises.SQL_DELETE_QUERY;
                createTableQuery = ModelPaises.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelPaises.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelTipoDocumentos.ModelTipoDocumentosSchema.TABLE_NAME :
                deleteTableQuery = ModelTipoDocumentos.SQL_DELETE_QUERY;
                createTableQuery = ModelTipoDocumentos.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelTipoDocumentos.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelTipoFigura.ModelTipoFiguraSchema.TABLE_NAME :
                deleteTableQuery = ModelTipoFigura.SQL_DELETE_QUERY;
                createTableQuery = ModelTipoFigura.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelTipoFigura.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelTipoVeiculos.ModelTipoVeiculosSchema.TABLE_NAME :
                deleteTableQuery = ModelTipoVeiculos.SQL_DELETE_QUERY;
                createTableQuery = ModelTipoVeiculos.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelTipoVeiculos.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelReboques.ModelReboquesSchema.TABLE_NAME :
                deleteTableQuery = ModelReboques.SQL_DELETE_QUERY;
                createTableQuery = ModelReboques.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelReboques.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelTipoOcorrencias.ModelTipoOcorrenciasSchema.TABLE_NAME :
                deleteTableQuery = ModelTipoOcorrencias.SQL_DELETE_QUERY;
                createTableQuery = ModelTipoOcorrencias.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelTipoOcorrencias.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelTextoRodape.ModelTextoRodapeSchema.TABLE_NAME :
                deleteTableQuery = ModelTextoRodape.SQL_DELETE_QUERY;
                createTableQuery = ModelTextoRodape.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelTextoRodape.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelLegislacao.ModelLegislacaoSchema.TABLE_NAME :
                deleteTableQuery = ModelLegislacao.SQL_DELETE_QUERY;
                createTableQuery = ModelLegislacao.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelLegislacao.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelEstados.ModelEstadosSchema.TABLE_NAME :
                deleteTableQuery = ModelEstados.SQL_DELETE_QUERY;
                createTableQuery = ModelEstados.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelEstados.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelMotivos.ModelMotivosSchema.TABLE_NAME :
                deleteTableQuery = ModelMotivos.SQL_DELETE_QUERY;
                createTableQuery = ModelMotivos.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelMotivos.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelTipoOrdemServico.ModelTipoOrdemServicoSchema.TABLE_NAME :
                deleteTableQuery = ModelTipoOrdemServico.SQL_DELETE_QUERY;
                createTableQuery = ModelTipoOrdemServico.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelTipoOrdemServico.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;case ModelTipoAccao1.ModelTipoAccao1Schema.TABLE_NAME :
                deleteTableQuery = ModelTipoAccao1.SQL_DELETE_QUERY;
                createTableQuery = ModelTipoAccao1.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelTipoAccao1.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelTipoAccao2.ModelTipoAccao2Schema.TABLE_NAME :
                deleteTableQuery = ModelTipoAccao2.SQL_DELETE_QUERY;
                createTableQuery = ModelTipoAccao2.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelTipoAccao2.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelParametrosPDA.ModelParametrosPDASchema.TABLE_NAME :
                deleteTableQuery = ModelParametrosPDA.SQL_DELETE_QUERY;
                createTableQuery = ModelParametrosPDA.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelParametrosPDA.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelTarifasMaximas.ModelTarifasMaximasSchema.TABLE_NAME :
                deleteTableQuery = ModelTarifasMaximas.SQL_DELETE_QUERY;
                createTableQuery = ModelTarifasMaximas.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelTarifasMaximas.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelRuas.ModelRuasSchema.TABLE_NAME :
                deleteTableQuery = ModelRuas.SQL_DELETE_QUERY;
                createTableQuery = ModelRuas.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelRuas.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelZonas.ModelZonasSchema.TABLE_NAME :
                deleteTableQuery = ModelZonas.SQL_DELETE_QUERY;
                createTableQuery = ModelZonas.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelZonas.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelCartaoResidente.ModelCartaoResidenteSchema.TABLE_NAME :
                deleteTableQuery = ModelCartaoResidente.SQL_DELETE_QUERY;
                createTableQuery = ModelCartaoResidente.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelCartaoResidente.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            case ModelTopMatriculas.ModelTopMatriculasSchema.TABLE_NAME :
                deleteTableQuery = ModelTopMatriculas.SQL_DELETE_QUERY;
                createTableQuery = ModelTopMatriculas.SQL_CREATE_QUERY;
                for(LinkedHashMap<String, String> record : responseObj.getRegistos()) {
                    insertRecordsQueries.add(ModelTopMatriculas.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                    idRecord++;
                }
                break;
            default: return 1;
        }

        // Remove tabela se existir
        if(tableExists)
            if(!mdbHelper.executeSqlStatement(deleteTableQuery))
                return 2;

        // Cria tabela
        if(!mdbHelper.executeSqlStatement(createTableQuery))
            return 3;

        // Popula tabela
        if(!mdbHelper.executeBatchInserts(insertRecordsQueries))
            return 4;

        return 0;
    }
}

package com.example.hugo.stc_android.Model.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Hugo on 14-07-2015.
 */
public class WebserviceUtils {

    public static List<String> getEstruturaCompleta() {
        List<String> fullStructure = new ArrayList<>();
        fullStructure.addAll(getEstruturaBase());
        fullStructure.addAll(getEstruturaRuas());
        fullStructure.addAll(getEstruturaCartaoResidente());
        fullStructure.addAll(getEstruturaTopMatricula());
        return fullStructure;
    }

    public static List<String> getEstruturaBase() {
        return new ArrayList<>(Arrays.asList(
                "Download_TipInfraccoes",
                "Download_Categorias",
                "Download_Cors",
                "Download_Fiscais",
                "Download_Marcas",
                "Download_PDAs",
                "Download_Danos",
                "Download_Parques",
                "Download_ViaComunicacaos",
                "Download_Paises",
                "Download_TipoDocumentos",
                "Download_TipoFigura",
                "Download_TipVeiculos",
                "Download_Reboques",
                "Download_TipOcorrencias",
                "Download_TextoRodape",
                "Download_Legislacao",
                "Download_Estados",
                "Download_Motivos",
                "Download_TipoOrdemServico",
                "Download_TipoAccao1",
                "Download_TipoAccao2",
                "Download_ParametrosPDA",
                "Download_TarifasMaximas"
        ));
    }

    public static List<String> getEstruturaRuas() {
        return new ArrayList<>(Arrays.asList(
                "Download_Ruas",
                "Download_Zonas"
        ));
    }

    public static List<String> getEstruturaCartaoResidente() {
        return new ArrayList<>(Arrays.asList(
                "Download_cartao_residente"
        ));
    }

    public static List<String> getEstruturaTopMatricula() {
        return new ArrayList<>(Arrays.asList(
                "Download_TopMatriculas"
        ));
    }

    public static List<String> getListWithId(LinkedHashMap<String, String> record, int idRecord) {
        List<String> values = new ArrayList<>();
        values.add(Integer.toString(idRecord));
        values.addAll(new ArrayList<>(record.values()));
        return values;
    }

    public static List<String> getListWithoutId(LinkedHashMap<String, String> record) {
        return new ArrayList<>(record.values());
    }
}

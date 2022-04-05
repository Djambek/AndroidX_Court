package com.example.courts_test_androidx;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Search_court_case_lib {
    private Map<String, String> msk_courts = Stream.of(new String[][] {
            { "Все суды", ""},
            {"mgs", "Московский городской суд"},
            {"babushkinskij", "Бабушкинский районный суд"},
            {"basmannyj", "Басманный районный суд"},
            {"butyrskij", "Бутырский районный суд"},
            {"gagarinskij", "Гагаринский районный суд"},
            {"golovinskij", "Головинский районный суд"},
            {"dorogomilovskij", "Дорогомиловский районный суд"},
            {"zamoskvoreckij", "Замоскворецкий районный суд"},
            {"zelenogradskij", "Зеленоградский районный суд"},
            {"zyuzinskij", "Зюзинский районный суд"},
            {"izmajlovskij", "Измайловский районный суд"},
            {"koptevskij", "Коптевский районный суд"},
            {"kuzminskij", "Кузьминский районный суд"},
            {"kuncevskij", "Кунцевский районный суд"},
            {"lefortovskij", "Лефортовский районный суд"},
            {"lyublinskij", "Люблинский районный суд"},
            {"meshchanskij", "Мещанский районный суд"},
            {"nagatinskij", "Нагатинский районный суд"},
            {"nikulinskij", "Никулинский районный суд"},
            {"ostankinskij", "Останкинский районный суд"},
            {"perovskij", "Перовский районный суд"},
            {"preobrazhenskij", "Преображенский районный суд"},
            {"presnenskij", "Пресненский районный суд"},
            {"savyolovskij", "Савёловский районный суд"},
            {"simonovskij", "Симоновский районный суд"},
            {"solncevskij", "Солнцевский районный суд"},
            {"taganskij", "Таганский районный суд"},
            {"tverskoj", "Тверской районный суд"},
            {"timiryazevskij", "Тимирязевский районный суд"},
            {"troickij", "Троицкий районный суд"},
            {"tushinskij", "Тушинский районный суд"},
            {"hamovnicheskij", "Хамовнический районный суд"},
            {"horoshevskij", "Хорошёвский районный суд"},
            {"cheryomushkinskij", "Черёмушкинский районный суд"},
            {"chertanovskij", "Чертановский районный суд"},
            {"shcherbinskij", "Щербинский районный суд"},
    }).collect(Collectors.collectingAndThen(
            Collectors.toMap(data -> data[1], data -> data[0]),
            Collections::<String, String> unmodifiableMap));

    private Map<String, String> msk_instance = Stream.of(new String[][] {
            {" ", ""},
            {"Первая", "1"},
            {"Апелляционная", "2"},
            {"Кассационная", "3"},
            {"Надзорная", "4"},
    }).collect(Collectors.collectingAndThen(
            Collectors.toMap(data -> data[0], data -> data[1]),
            Collections::<String, String> unmodifiableMap));

    private Map<String, String> msk_type_case = Stream.of(new String[][] {
            {"Все типы судопроизводств", ""},
            {"Административное", "1"},
            {"Гражданское", "2"},
            {"Об административных правонарушениях", "3"},
            {"Первичные документы", "4"},
            {"Производства по материалам", "5"},
            {"Уголовное", "6"},
    }).collect(Collectors.collectingAndThen(
            Collectors.toMap(data -> data[0], data -> data[1]),
            Collections::<String, String> unmodifiableMap));
    Document res;
    String type_case;
    String participant;
    String number_input_document;
    String unique_id;
    String type_trial;
    String number_case;
    String type_instance;

    Search_court_case_lib(String city, String type_trial, String unique_id, String type_instance,
                   String number_input_document, String number_case,
                   String participant, String type_case){
        if (city.equals("Москва")){
            this.type_trial = msk_courts.get(type_trial) == null ? "":msk_courts.get(type_trial); // суд
            this.type_case = msk_type_case.get(type_case) == null ? "":msk_type_case.get(type_case); // производство
            this.type_instance = msk_instance.get(type_instance) == null ? "":msk_instance.get(type_instance); // Инстанция
        }
        this.unique_id = unique_id.length() > 0 ? unique_id:""; // Уникальный идентификатор дела
        this.number_input_document = number_input_document.length() > 0 ? number_input_document:""; // номер входящего документа
        this.number_case = number_case.length() > 0 ?  number_case:""; // номер дела
        this.participant = participant.length() > 0 ? participant:""; // стороны



    }
    public int get_count_of_page() throws IOException {
        String url = "https://mos-gorsud.ru/rs/golovinskij/search?formType=shortForm&courtAlias="+this.type_trial+
                "&uid="+unique_id+"&instance="+this.type_instance+"&processType="+this.type_case+"&letterNumber="+
                this.number_input_document+"&caseNumber="+this.number_case+"&participant="+this.participant;
        this.res = Jsoup.connect(url).get();

        try {
            String string_digital = res.getElementById("paginationForm").text();
            System.out.println(string_digital);
            int count_page =Integer.parseInt(string_digital.substring(12, string_digital.length()-1));
            return count_page;
        }
        catch (NullPointerException | NumberFormatException  e){
            return 1;
        }

    }

    public ArrayList<ArrayList<String>> search_all() throws IOException{

        return null;
    }
    public ArrayList<ArrayList<String>> search(int page) throws IOException {
        page = Math.max(page, 1);
        String url = "https://mos-gorsud.ru/rs/golovinskij/search?formType=shortForm&courtAlias="+this.type_trial+
                "&uid="+this.unique_id+"&instance="+this.type_instance+"&processType="+this.type_case+"&letterNumber="+
                this.number_input_document+"&caseNumber="+this.number_case+"&participant="+this.participant+"&page="+ page;
        this.res = Jsoup.connect(url).get();
        Log.d("URL", url);
        Elements table = this.res.select("table.custom_table").select("tbody");
        ArrayList<ArrayList<String>> answer = new ArrayList<ArrayList<String>>();
        for(Element el: table.select("tr")){
            ArrayList<String> tmp_list = new ArrayList<String>();
            for(Element td: el.select("td")){
                if(!td.text().equals("")){
                    tmp_list.add(td.text());
                }
            }
            tmp_list.add("https://mos-gorsud.ru/"+el.select("a.detailsLink").attr("href"));
            answer.add(tmp_list);
        }
        return answer;
    }


}

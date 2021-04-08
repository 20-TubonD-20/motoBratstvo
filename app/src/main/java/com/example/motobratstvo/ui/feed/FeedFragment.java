package com.example.motobratstvo.ui.feed;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.motobratstvo.R;
import com.example.motobratstvo.ScrActivity.ScrActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FeedFragment extends Fragment {
    ArrayList<News> news = new ArrayList<News>();
//    String buffTitle, buffText = new String();
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/news");

    public void setInitialData(){
/*
        news.add(new News ("Перегородивший Суэцкий канал танкер сняли с мели ", "Перегородивший Суэцкий канал танкер Minerva Nike сняли с мели. Об этом сообщает РИА Новости со ссылкой на осведомленный источник.В источнике", R.drawable.icon));
        news.add(new News ("Мосгорсуд оставил под арестом жену бизнесмена Шпигеля ", "Защитникам Елены Шпигель, супруги бизнесмена Бориса Шпигеля, арестованной в рамках коррупционного дела экс-губернатора Пензенской области Ивана Белозерцева, не удалось оспорить в суде её арест.В источнике\n" +
                "\n", R.drawable.icon));
        news.add(new News ("Кремль исключил создание особых условий для Навального в колонии ", "Создание каких-либо исключительных условий для Алексея Навального, находящегося в СИЗО, невозможно, заявил пресс-секретарь президента Дмитрий Песков.В источнике", R.drawable.icon));
        news.add(new News ("Зеленский назвал вступление в НАТО единственным путем к миру в Донбассе ", "Президент Украины Владимир Зеленский обсудил с генсеком НАТО Йенсом Столтенбергом обострение ситуации с безопасностью в Донбассе и заявил, что Североатлантический альянс – это единственный путь к окончанию конфликта в Донбассе, сообщает во вторник офис главы украинского государства.В источнике", R.drawable.icon));
        news.add(new News ("Перегородивший Суэцкий канал танкер сняли с мели ", "Перегородивший Суэцкий канал танкер Minerva Nike сняли с мели. Об этом сообщает РИА Новости со ссылкой на осведомленный источник.В источнике", R.drawable.icon));
        news.add(new News ("Мосгорсуд оставил под арестом жену бизнесмена Шпигеля ", "Защитникам Елены Шпигель, супруги бизнесмена Бориса Шпигеля, арестованной в рамках коррупционного дела экс-губернатора Пензенской области Ивана Белозерцева, не удалось оспорить в суде её арест.В источнике\n" +
                "\n", R.drawable.icon));
        news.add(new News ("Кремль исключил создание особых условий для Навального в колонии ", "Создание каких-либо исключительных условий для Алексея Навального, находящегося в СИЗО, невозможно, заявил пресс-секретарь президента Дмитрий Песков.В источнике", R.drawable.icon));
        news.add(new News ("Зеленский назвал вступление в НАТО единственным путем к миру в Донбассе ", "Президент Украины Владимир Зеленский обсудил с генсеком НАТО Йенсом Столтенбергом обострение ситуации с безопасностью в Донбассе и заявил, что Североатлантический альянс – это единственный путь к окончанию конфликта в Донбассе, сообщает во вторник офис главы украинского государства.В источнике", R.drawable.icon));
        news.add(new News ("В Москве в ближайшее время запустят «народный каршеринг» ", "Суть проекта О том, что московские власти готовятся в ближайшее время запустить платформу «народного каршеринга», РБК рассказали два источника на рынке автомобильных арендных сервисов.В источнике", R.drawable.icon));
        news.add(new News ("Перегородивший Суэцкий канал танкер сняли с мели ", "Перегородивший Суэцкий канал танкер Minerva Nike сняли с мели. Об этом сообщает РИА Новости со ссылкой на осведомленный источник.В источнике", R.drawable.icon));
        news.add(new News ("Мосгорсуд оставил под арестом жену бизнесмена Шпигеля ", "Защитникам Елены Шпигель, супруги бизнесмена Бориса Шпигеля, арестованной в рамках коррупционного дела экс-губернатора Пензенской области Ивана Белозерцева, не удалось оспорить в суде её арест.В источнике\n" +
                "\n", R.drawable.icon));
        news.add(new News ("Кремль исключил создание особых условий для Навального в колонии ", "Создание каких-либо исключительных условий для Алексея Навального, находящегося в СИЗО, невозможно, заявил пресс-секретарь президента Дмитрий Песков.В источнике", R.drawable.icon));
        news.add(new News ("Зеленский назвал вступление в НАТО единственным путем к миру в Донбассе ", "Президент Украины Владимир Зеленский обсудил с генсеком НАТО Йенсом Столтенбергом обострение ситуации с безопасностью в Донбассе и заявил, что Североатлантический альянс – это единственный путь к окончанию конфликта в Донбассе, сообщает во вторник офис главы украинского государства.В источнике", R.drawable.icon));
        news.add(new News ("В Москве в ближайшее время запустят «народный каршеринг» ", "Суть проекта О том, что московские власти готовятся в ближайшее время запустить платформу «народного каршеринга», РБК рассказали два источника на рынке автомобильных арендных сервисов.В источнике", R.drawable.icon));
        news.add(new News ("Перегородивший Суэцкий канал танкер сняли с мели ", "Перегородивший Суэцкий канал танкер Minerva Nike сняли с мели. Об этом сообщает РИА Новости со ссылкой на осведомленный источник.В источнике", R.drawable.icon));
        news.add(new News ("Мосгорсуд оставил под арестом жену бизнесмена Шпигеля ", "Защитникам Елены Шпигель, супруги бизнесмена Бориса Шпигеля, арестованной в рамках коррупционного дела экс-губернатора Пензенской области Ивана Белозерцева, не удалось оспорить в суде её арест.В источнике\n" +
                "\n", R.drawable.icon));
        news.add(new News ("Кремль исключил создание особых условий для Навального в колонии ", "Создание каких-либо исключительных условий для Алексея Навального, находящегося в СИЗО, невозможно, заявил пресс-секретарь президента Дмитрий Песков.В источнике", R.drawable.icon));
        news.add(new News ("Зеленский назвал вступление в НАТО единственным путем к миру в Донбассе ", "Президент Украины Владимир Зеленский обсудил с генсеком НАТО Йенсом Столтенбергом обострение ситуации с безопасностью в Донбассе и заявил, что Североатлантический альянс – это единственный путь к окончанию конфликта в Донбассе, сообщает во вторник офис главы украинского государства.В источнике", R.drawable.icon));
        news.add(new News ("В Москве в ближайшее время запустят «народный каршеринг» ", "Суть проекта О том, что московские власти готовятся в ближайшее время запустить платформу «народного каршеринга», РБК рассказали два источника на рынке автомобильных арендных сервисов.В источнике", R.drawable.icon));
        */

 /*       for (final int[] i = {1}; i[0] < 1000000000; i[0]++) {
            //String buffTitle = mDatabase.child("news").child(Integer.toString(i)).child("title").getKey();
            //String buffText = mDatabase.child("news").child(Integer.toString(i)).child("text").getKey();
            final String[] buffTitle = new String[1];
            final String[] buffText = new String[1];
            mDatabase.child(Integer.toString(i[0])).child("title").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                        buffTitle[0] =  "null";
                    }
                    else {
                        buffTitle[0] =  String.valueOf(task.getResult().getValue());
                        Log.d("firebase", buffTitle[0]);

                    }
                }
            });

            mDatabase.child("news").child(Integer.toString(i[0])).child("text").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                        buffText[0] =  "null";
                    }
                    else {
                        buffText[0] =  String.valueOf(task.getResult().getValue());
                        Log.d("firebase", buffText[0]);

                    }
                }
            });
            System.out.println(buffTitle[0]);
            System.out.println(buffText[0]);

            news.add(new News (buffTitle[0], buffText[0]));
        }

  */
        //news.add(new News (buffTitle[0], buffText[0]));
        ScrActivity scrActivity = (ScrActivity) getActivity();
        scrActivity.refreshData();
        news = scrActivity.data;

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_feed, container, false);

        RecyclerView recView = root.findViewById(R.id.list);
        // начальная инициализация списка
        setInitialData();
        System.out.println(news.size());
        if(news.size() != 0) {
            System.out.println(news.get(0).getTitle());
            System.out.println(news.get(0).getText());
        }
        // создаем адаптер
        RecyclerViewAdapter adapter = new RecyclerViewAdapter((Context) getActivity(), news);
        // устанавливаем для списка адаптер
        recView.setAdapter(adapter);

        return root;
    }

    public static int getSize(){
        return 1;
    }
}
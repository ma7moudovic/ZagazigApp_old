package com.sharkawy.zagazigapp.dataModels;

/**
 * Created by T on 4/22/2016.
 */
public class Tag {
    String Tag ;
//    String [] TAGS = {"مطاعم","كافيهات","سينمات","هدوم ولادى","هدوم بناتى","هدوم اطفال","موبيلات ولابات","جيم شبابي","جيم بناتى","مراكز تجميل","قاعات افراح","ستوديو تصوير","فوتوجرافيك","مستشفيات","عيادات","خدمات عربيات"};
    String [] TAGS = {"سندوتشات" ,
            "بيتزا" ,
            "كشرى ",
            "مشويات ",
            "حلويات ",
            "كريب ",
            "اكل بيتى" ,
            "هدوم خروج" ,
            "بدل ",
            "احزية ",
            "توكيلات ",
            "هدوم خروج",
            "بيجامات و لانجرى",
            "اكسسوارات و ميك اب",
            "شنط و احذية"
            ,"خدمات عامة" };

    public Tag() {
    }

    public Tag(String tag) {
        Tag = tag;
    }

    public String getTag() {
        return TAGS[Integer.parseInt(Tag)];
    }

    public void setTag(String tag) {
        Tag = tag;
    }
}

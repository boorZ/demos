package com.example.noasksldemo.sz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.noasksldemo.utils.FileUtils;
import com.example.noasksldemo.utils.RestTemplateUtils;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述
 *
 * @author 周林
 * @version 1.0
 * @date 2021/6/22 18:07
 */
@Slf4j
public class QaMain {
    @Test
    public void test() throws InterruptedException {
//        List<String> list = new ArrayList<>();
        String read = FileUtils.read("szKeyWord/newQa");
        List<String> regex = regex("- intent.*?(?=- intent)", read);
        for (String s : regex) {
            String s1 = regexOne("(?<=- intent: ).*(?=examples)", s);
            String s2 = regexOne("(?<=examples:).*", s);
            List<String> search = search(Arrays.asList(s2.split(",")), "", s1);
//            list.addAll(search);
            generateFile("nameList.txt", StringUtils.join(search, "\n"));
            Thread.sleep(2000);
        }
//        String join = StringUtils.join(list, "\n");
//        System.out.println(join);
//        System.out.println(list.size());
        System.out.println();
    }

    protected static String GENERATE_PATH = "D:" + File.separator + "qa" + File.separator;
    /**
     * 生成文件
     *
     * @param fileName
     * @param content
     * @throws IOException
     */
    public static void generateFile(String fileName, String content) {
        // 文件目录
        Path path = Paths.get(GENERATE_PATH + fileName);
        //追加写模式
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND)){
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String regexOne(String regex, String text) {
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(text);
        boolean b = matcher.find();
        return b ? matcher.group(0) : "";
    }
    private static List<String> regex(String regex, String text) {
        List<String> list = new ArrayList<>();
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(text);
        while (matcher.find()) {
            list.add(matcher.group(0));
        }
        return list;
    }

    public static void main(String[] args) {
//        String str = "", judgeId = "", judgeName = "";
//        List<String> searchList;

        List<JudgeRep> judge = getJudgeExtends();
        List<String> list = new ArrayList<>();
        for (JudgeRep res : judge) {
            String str = res.searchList;
            List<String> searchList = Arrays.asList(str.split(","));
            List<String> search = search(searchList, res.judgeId, res.judgeName);
            list.addAll(search);
        }
        String join = StringUtils.join(list, "\n");
        System.out.println(join);

        System.out.println(list.size());
    }

    public static List<JudgeRep> getJudgeExtends() {
        List<JudgeRep> list = new ArrayList<>();
        list.add(JudgeRep.builder().judgeId("77014221652492288").judgeName("离婚办理房屋产权过户是否征收个人所得税")
                .searchList("离婚办理房屋产权过户是否征收个人所得税,离婚了房子过户收不收个税,我和我老公离婚了，房子过户交不交个人所得税,离婚财产分割的房子过户有没有个人所得税,离婚房子过户征不征收个税,因为离婚导致的房子过户征收不征收个税呀,我家的房子因为我和我老公离婚过户到我头上了，我要不要交个税,离婚的财产分割，涉及房屋的，税务局收不收个税,如果我和我老公离婚了，房子要过户给我老公，他交不交个人所得税,我的房子过户了，因为离婚过的，是不是要交个税,夫妻双方共有的房子因为离婚过户到一方身上了，交不交个税  ").build());
        list.add(JudgeRep.builder().judgeId("77016613819252736").judgeName("房屋附属设备和配套设施是否计入房产原值")
                .searchList("房屋附属设备和配套设施是否计入房产原值,房产原值包不包括房屋附属设备和配套设施,房产原值包括房屋附属设备和配套设施吗,房产原值有没有房屋附属设备和配套设施,房屋附属设备和配套设施算不算到房产原值里面,房屋附属设备和配套设施属不属于房产原值的,计算房产原值的时候房屋附属设备和配套设施算不算,房屋附属设备和配套设施属于房产原值吗,房屋附属设备和配套设施包括在房产原值里面吗,房屋附属设备和配套设施在计算房产原值的时候算不算,计算房产原值的时候算不算上房屋附属设备和配套设施").build());
        list.add(JudgeRep.builder().judgeId("77017456023961600").judgeName("无需办理个人所得税年度汇算清缴的情形")
                .searchList("无需办理个人所得税年度汇算清缴的情形,什么情况下不用办理个税汇算,哪些情况不用办理个人所得税汇算,不需要办理个税汇算的情形有哪些,个人所得税汇算清缴不需要办理的情形有哪些,无需办理个税汇算的情形,哪些情形不需要办理个税汇算,我在什么情况下不用办理个人所得税汇算,什么情形下我不需要办理个税汇算清缴,纳税人在什么情形下可以不用办理个税汇算,哪些情形下的个税汇算不需要办理").build());
        list.add(JudgeRep.builder().judgeId("77017307331690496").judgeName("如何区分工资、薪金所得与劳务报酬所得")
                .searchList("如何区分工资、薪金所得与劳务报酬所得,工资薪金和劳务报酬有什么区别,工资薪金和劳务报酬怎么区分,工资薪金和劳务报酬不一样的地方是什么,我要咋个区分工资薪金和劳务报酬,咋个区分工资薪金和劳务报酬,工资薪金和劳务报酬怎样可以区分,怎样区分工资薪金和劳务报酬,我可以怎么区分工资薪金和劳务报酬,工资薪金和劳务报酬不同的地方是什么,工资薪金和劳务报酬有什么不同").build());
        return list;
    }

    public static List<JudgeRep> getJudge() {
        List<JudgeRep> list = new ArrayList<>();
        list.add(JudgeRep.builder().judgeId("81818523047821312").judgeName("在哪儿能看到自己的社保缴纳情况")
                .searchList("在哪可以看我的社保缴纳情况,社保缴纳情况哪里看,社保缴纳哪里看,我的社保缴纳是不是可以看,社保交了咋个查,交了社保咋个查,交了社保那可以查,我交的社保是不是可以在那儿看得到,社保费的缴纳从哪里可以查询,社会保险费的缴纳情况在哪里可以查,哪儿查社保的交的钱,社保交的钱在哪儿看").build());
        list.add(JudgeRep.builder().judgeId("4797437116575").judgeName("一照一码户信息变更")
                .searchList("一照一码的信息怎么变更,变更一照一码的信息,怎么变更一照一码的信息,怎么做一照一码的信息变更,一照一码的变更流程,变更一照一码的流程").build());
        list.add(JudgeRep.builder().judgeId("75748228520214528").judgeName("免征房产税的房屋包括哪些")
                .searchList("房产税的免征范围,房产税免征范围,免征范围房产税,免征房产税的有那些,有那些可以免征房产税,免征房产税的适用范围,什么情况下会免征房产税,房产税的免征政策,哪些房产免征房产税,哪些房产不交房产税,哪些房屋免征房产税,哪些房屋不交房产税").build());
        list.add(JudgeRep.builder().judgeId("75745089659338752").judgeName("农村饮水工程适用哪些税收优惠政策")
                .searchList("农村饮水工程适用哪些税收优惠政策,饮水工程在农村适用的税收优惠政策,税收优惠政策适用于农村的饮水工程,饮水工程有那些税收优惠政策适用于农村,饮水工程有那些税收减免适用于农村,饮水工程减免政策适用于农村,农村饮水工程可以享受哪些减免,农村饮水工程有哪些优惠可以享受").build());
        list.add(JudgeRep.builder().judgeId("75749765059444736").judgeName("印花税小于1元是不是不用交")
                .searchList("印花税小于1元是不是不用交,1元是不是不用交印花税,印花税1元不用交,1元怎么交印花税,1元如何交印花税,1元咋交印花税,不到1元的贴花交不交印花税,不到1元的贴花是不是不交印花税,小于1元的贴花是不是不交印花税,小于1元的贴花交不交印花税,贴花小于1元是不是不用交,1元贴花是不是不用交印花税,印花税1元贴花不用交,1元贴花怎么交印花税,1元贴花如何交印花税,1元贴花咋交印花税").build());
        list.add(JudgeRep.builder().judgeId("75750135438508032").judgeName("印花税逾期要不要交滞纳金")
                .searchList("印花税逾期要不要交滞纳金,逾期缴纳印花税有没有滞纳金,我忘了交印花税是不是要交滞纳金,印花税是否要交滞纳金,过了申报期印花税有没有滞纳金,过了申报期印花税交不交滞纳金").build());
        list.add(JudgeRep.builder().judgeId("75746590607802368").judgeName("非税收入包括哪些范围")
                .searchList("非税收入包括哪些范围,非税收入包括哪些,非税收入是啥子,啥子是非税收入,那些收入是非税收入,那些收入可以归于非税收入,那些收入可以算是非税收入").build());
        list.add(JudgeRep.builder().judgeId("75750820821336064").judgeName("基本医疗保险费的征缴范围是哪些")
                .searchList("基本医疗保险费的征缴范围是哪些,医保的缴纳范围是什么,基本医疗保险费的缴纳范围是什么,缴纳医保的范围是什么,缴纳基本医疗保险费的范围是什么").build());
        list.add(JudgeRep.builder().judgeId("75841341053992960").judgeName("哪些人应该缴纳基本医疗保险")
                .searchList("哪些人应该缴纳基本医疗保险,哪些人应该缴纳医保,哪些人要交医保,哪些人要交基本医疗保险费,哪些人要缴医保,哪些人要缴基本医疗保险费,什么人应该缴纳基本医疗保险,什么人应该缴纳医保,什么人要交医保,什么人要交基本医疗保险费,什么人要缴医保,什么人要缴基本医疗保险费").build());
        list.add(JudgeRep.builder().judgeId("95858248255537152").judgeName("个人代开发票可否开具完税证明")
                .searchList("个人代开发票可否开具完税证明,代开发票的个人开具完税证明行不行,个人代开发票是不是可以开完税证明").build());
        list.add(JudgeRep.builder().judgeId("77016157525114880").judgeName("纳税人销售二手车是否征收增值税")
                .searchList("纳税人[销售](action)二手车是否征收增值税,二手车纳税人[销售](action)是否征收增值税,增值税二手车纳税人[销售](action)是否征收,增值税二手车纳税人[销售](action)要不要征收,增值税二手车纳税人[销售](action)咋征收,增值税二手车纳税人[销售](action)怎么征收,[销售](action)旧车缴不缴纳增值税,[出售](action)二手车缴不缴增值税,[卖](action)二手车缴不缴增值税").build());
        list.add(JudgeRep.builder().judgeId("95668836804591616").judgeName("亏损状态的新企业可否享受研发费用加计扣除")
                .searchList("亏损状态的新企业可否享受研发费用加计扣除,新企业处于亏损状态的可否享受研发费用加计扣除,新企业亏损状态可否享受研发费用加计扣除,新企业享受研发费用加计扣除是否必须是亏损状态,亏损企业可以加计扣除研发费用么,亏损状态的企业是否可以加计扣除研发费").build());
        list.add(JudgeRep.builder().judgeId("77013842239946752").judgeName("全年一次性奖金包括哪些范围")
                .searchList("全年一次性奖金包括哪些范围,一次性奖金全年包括哪些范围,一次性奖金全年包括哪些属于,一次性奖金全年包括哪些收入属于,哪些收入属于一次性奖金全年,全年一次性奖金的定义是啥,什么是全年一次性奖金").build());
        list.add(JudgeRep.builder().judgeId("75750132614692864").judgeName("新的烟叶税纳税申报表什么时候施行")
                .searchList("最新的烟叶税纳税申报表什么时候可以用,烟叶税纳税申报表最新的什么时候可以用,什么时候可以用烟叶税纳税申报表最新的,最新的什么时候可以用烟叶税纳税申报表,可以用烟叶税纳税申报表最新的什么时候").build());
        list.add(JudgeRep.builder().judgeId("75747097891045376").judgeName("实名采集有哪些方式")
                .searchList("实名采集怎么采集,采集实名怎么采集,采集实名咋整,采集实名咋办理,采集实名到哪儿采,什么是实名采集").build());
        list.add(JudgeRep.builder().judgeId("75747096000462848").judgeName("实名采集需要在什么时候办理")
                .searchList("什么时候应该办理实名采集,啥时候办理实名采集,实名采集办理啥时候,办理实名采集啥时候,实名采集发生在什么阶段").build());
        list.add(JudgeRep.builder().judgeId("77016254468063232").judgeName("个人的办学习班收入如何缴纳个人所得税")
                .searchList("个人的办学习班收入如何缴纳个人所得税,办学习班的个人的收入如何缴纳个人所得税,如何缴纳个人所得税办学习班的个人的收入,是不是要个人所得税办学习班的个人收入,个人办班怎么缴纳个税").build());
        list.add(JudgeRep.builder().judgeId("84798331098759168").judgeName("企业闲置房屋是否需要缴纳房产税")
                .searchList("企业闲置房屋是否需要缴纳房产税,企业闲置房屋要不要缴房产税,房产税要不要缴企业闲置房屋,企业不用的房子交不交房产税,企业不用的房子要不要交房产税,企业不用的房子缴不缴房产税,企业不用的房子要不要缴房产税,闲置的房产需不需要缴房产税,企业闲置的房屋需不需要缴纳房产税").build());
        list.add(JudgeRep.builder().judgeId("84800001128005632").judgeName("软件产品的增值税优惠")
                .searchList("软件产品的增值税优惠,软件产品的增值税减免,增值税减免软件产品,减免增值税软件产品,软件产品减免增值税,软件产品优惠增值税").build());
        list.add(JudgeRep.builder().judgeId("75749445395808256").judgeName("个税可以抵扣房贷利息吗")
                .searchList("个税可以抵扣房贷利息吗,个税能不能抵扣房贷利息,个人所得税能不能抵扣房贷利息,房贷利息在计算个税时可以抵扣吗,房贷利息在计算个人所得税时可以抵扣吗,房贷利息在计算个税时能抵扣吗,房贷利息在计算个人所得税时能抵扣吗").build());
        list.add(JudgeRep.builder().judgeId("75811458631335936").judgeName("一般纳税人可以转成小规模纳税人吗")
                .searchList("一般纳税人可以转成小规模纳税人吗,一般纳税人能不能转成小规模纳税人,一般纳税人能不能转小规模纳税人,一般纳税人能转换成小规模纳税人吗,一般纳税人能不能转换成小规模纳税人,一般纳税人可以转换成小规模纳税人,一般纳税人转小规模纳税人的条件").build());
        list.add(JudgeRep.builder().judgeId("77017307961884672").judgeName("如何申请个人所得税退税")
                .searchList("如何申请个人所得税退税,如何在个人所得税APP上办理退税,个税退税如何操作,我要退我的个税怎样操作,个税app上怎样办理退税,个税app上如何退税,个人所得税app里怎样退税,个人所得税app里申请退税").build());
        list.add(JudgeRep.builder().judgeId("84257279398903808").judgeName("开具电子专票后，客户退货或者发现开票有误，电子发票能作废吗")
                .searchList("开具电子专票后，客户退货或者发现开票有误，电子发票能作废吗？,发生销售退货已经开具的电子专票能否作废,电子专票开错了能否作废重开,电子专票在发生退货时能作废么,发生销售退回时能否将已经开好的电子专票作废,电子专票开具的金额有误能否作废,电子专票开错了能作废重开么,开错的电子专票能作废吗,错开的电子专票可以作废吗,已开的电子专票可以在销售退货时作废么,怎样作废已经开错的电子专票,如何处理因退货而导致的电子专票的作废").build());
        list.add(JudgeRep.builder().judgeId("83333287921057792").judgeName("企业社保费在哪里签订三方协议")
                .searchList("企业社保费在哪里签订三方协议？,企业缴纳社保费签订三方协议在哪儿签,单位缴社保签三方协议在什么地方签,单位社保费的三方协议在哪儿签,在哪个地方签订社保费三方协议,什么地方可以签订企业缴纳社会保险费的三方协议,在哪个地方签订社保缴纳的三方协议,在哪儿可以签社保三方协议,企业缴纳社会保险费的三方协议在哪儿签").build());
        list.add(JudgeRep.builder().judgeId("77013706702061568").judgeName("残保金是否有免征优惠")
                .searchList("残保金是否有免征优惠？,残疾人就业保障金有什么优惠政策,帮我查询下残保金的优惠政策,残疾人就业保障金有啥免税政策么,残保金达到什么条件可以免征,残保金有免征的优惠政策么,残疾人就业保障金有啥可以免征的政策,什么条件能让我不缴残保金,帮我找下残保金相关免税政策,查询一下有没有残保金的优惠政策").build());
        list.add(JudgeRep.builder().judgeId("96946544011378688").judgeName("2021年四川省免征房土两税的纳税人申请条件是什么")
                .searchList("2021年四川省免征房土两税的纳税人申请条件是什么？,四川2021年可以免征房土两税的条件有哪些,今年四川免征房产税的纳税人资格有哪些,2021年四川免征土地使用税的条件有什么,帮我查下四川今年免征房产税和土地使用税的企业条件有哪些,查询下四川2021年免房产税和城镇土地使用税的条件是什么,2021年四川免房产税的限制条件有哪些,2021年四川免征房土两税的文件中有哪些纳税人可以享受免税优惠,今年成都免征房产税的纳税人要符合什么条件,今年免除房土两税的四川企业需要达到什么条件,享受房产税和城镇土地使用税都免税的优惠政策需要达到什么条件,今年如何才能享受免征房土两税的政策").build());

        list.add(JudgeRep.builder().judgeId("77014677054291968").judgeName("如何缴纳个人租赁房屋又转租取得所得的个人所得税")
                .searchList("如何缴纳个人租赁房屋又转租取得所得的个人所得税,我把房子转租怎么交个税,转租的房子个税怎么交,转租的房屋个人所得税怎么交,转租的房屋个税怎么交,转租的房子个人所得税怎么交,我把房屋转租怎么交个人所得税,我租的房子我又租给另外个人，个人所得税怎么交,那个租咧房子又租给其他人，咋个交个税咧,租的房子转租给别人怎么交个税,租的房屋转租给其他人个税怎么交").build());
        list.add(JudgeRep.builder().judgeId("86593670004867072").judgeName("个人出租车辆给公司的增值税税率是多少")
                .searchList("个人出租车辆给公司的增值税税率是多少,我自己的车子租给公司，增值税税率好多咧,把我自己的汽车租给公司，增值税的税率是好多,公司租我的车子，增值税税率是多少,公司租我的汽车，增值税税率是多少,我咧车子租给公司了，增值税的税率是好多,我把车子租给公司用，增值税的税率是什么,我个人的车子出租给公司使用，增值税的税率是多少,公司租我个人的汽车，增值税税率是好多,单位租我的车子，增值税税率是什么,我的车子租给单位用，增值税税率是好多").build());
        list.add(JudgeRep.builder().judgeId("77013802963435520").judgeName("如何缴纳个人出租住房的个人所得税")
                .searchList("如何缴纳个人出租住房的个人所得税,我出租住房怎么交个人所得税,我出租住房怎么交个税,我出租我自己住的房屋怎么交个人所得税,我出租我自己住的房屋怎么交个税  ,出租我自己住的房屋怎么交个税,出租我自己住的房子怎么交个税,出租我自己住的房屋怎么交个人所得税,出租我自己住的房子怎么交个人所得税,个人出租住房个人所得税怎么交,个人出租住房个税怎么交").build());
        list.add(JudgeRep.builder().judgeId("75748181038596096").judgeName("个人出租住房一次性收取全年租金可否一次性代开全年租金的发票")
                .searchList("个人出租住房一次性收取全年租金，去大厅代开发票是每个月去开，还是可以一次性代开全年租金的发票？,我出租住房收的一年的租金，代开发票是要每月去开还是一次性开一年的,出租住房收到的年租金，代开发票是要每月去开还是一次性开一年的,出租住房收的年租金，是要每月去税务局代开发票还是可以直接开一年的,我出租的住房是按年收的钱，发票是每月开还是可以直接开一年的,我收的出租住房的年房租，开发票是要每月去税务局开还是可以一次性开一年的").build());
        list.add(JudgeRep.builder().judgeId("75749588621852672").judgeName("个人出租不动产去哪里代开发票")
                .searchList("个人出租不动产去哪里代开发票？,个人出租不动产在什么地方代开发票,我出租的房子代开发票在什么地方,不动产出租方是个人在什么地方代开发票,个人出租不动产代开发票在什么地方,我出租的房屋在什么地方打开发票,不动产出租方是个人代开发票在什么地方,个人出租不动产在哪儿代开发票,个人出租不动产代开发票在哪儿,我出租的不动产要代开发票在哪儿开,我出租的不动产要在哪里代开发票").build());
        list.add(JudgeRep.builder().judgeId("77015208651915264").judgeName("新能源汽车享受免征车辆购置税的范围包括哪些")
                .searchList("新能源汽车享受免征车辆购置税的范围包括哪些？,哪些新能源车子可以免征车辆购置税,哪些新能源车子可以免征车购税,可以免征车辆购置税的新能源汽车有哪些,可以免征车购税的新能源汽车有哪些,免征车购税的新能源的车辆有些什么,免征车辆购置税的新能源的车辆有些什么,免征车购税的新能源车子的范围是什么").build());
        list.add(JudgeRep.builder().judgeId("75811365995937792").judgeName("空白发票丢失怎么处理")
                .searchList("还没用的发票丢了怎么办,没用的发票掉了怎么办,发票掉了怎么处理，空白的,没填的发票丢失了怎么处理,没填写的发票掉了怎么处理,没使用的发票遗失了怎么办,发票遗失，空白的,空白发票遗失怎么弄,空白发票掉了怎么处理,掉了空白的发票怎么弄").build());
        list.add(JudgeRep.builder().judgeId("75746893001392128").judgeName("增值税专用发票丢失，如何处理")
                .searchList("专票掉了怎么办,发票掉了，是专票怎么处理,怎么处理增值税专票掉了,专用发票遗失要怎么处理,增值税专用发票遗失要怎么办,增值税专票遗失怎么处理,增值税专票掉了怎么办,专用发票掉了怎么处理,增值税专用发票遗失怎么处理,专票掉了怎么处理").build());
        list.add(JudgeRep.builder().judgeId("75841316836081664").judgeName("发票的基本联次包括哪些")
                .searchList("发票有哪几联,发票联次有些什么,发票包括哪些联次,发票的联次是哪些,发票有哪些联次,发票有些什么联次,发票的联次包括什么,发票的联次包括哪些,发票包括联次有哪些,发票的联次").build());
        list.add(JudgeRep.builder().judgeId("75747816164556800").judgeName("如何查询增值税发票真伪")
                .searchList("怎么查发票真伪,发票的真假怎么查,发票的真伪怎么查,在哪儿查发票真假,我咋个查发票是不是假的呢,发票是不是假的怎么查,发票的真假咋个查,怎么查发票真假,发票真的假的怎么查,怎么鉴定发票真假").build());
        list.add(JudgeRep.builder().judgeId("75747796794212352").judgeName("一般纳税人开具专票，信息开错了，跨月了，怎么红冲发票")
                .searchList("我是一般纳税人，开的专票开错了，但是跨月了怎么红冲,一般纳税人跨月了才发现专票开错了，怎么红冲   - 一般纳税人专票开错了，跨月了，怎么红字冲销,一般纳税人跨月了才发现专票开错了，怎么红冲,专票开错了，跨月才发现，一般纳税人怎么红冲,跨月了才发现专票开错了的一般纳税人怎么红冲发票,我们公司是一般纳税人，开的专票跨月了发现开错了，怎么红字冲销,怎么红字冲销发票，一般纳税人开的已经跨月了的专票，因信息开错,一般纳税人跨月的专票开的有问题怎么红冲,一般纳税人开错专票了，但是跨月了怎么红冲").build());
        list.add(JudgeRep.builder().judgeId("84257279398903808").judgeName("开具电子专票后，客户退货或者发现开票有误，电子发票能作废吗")
                .searchList("发现开票有误，开具的电子专票能作废吗,电子专票在发现开票有误后能作废吗,发现开票有误，开的电子专票可以作废吗,发现开票有误后开的电子专票能不能作废,开具的电子专票在发现开票有误后能不能作废,发现开票有误了，开具的电子专票能作废吗,电子专票在发现开票有误后能作废吗,发现开票有误，开的电子专票可以作废吗,发现开票有误后开的电子专票能不能作废,开具的电子专票在发现开票有误后能不能作废  ").build());
        list.add(JudgeRep.builder().judgeId("77014393184845824").judgeName("超比例缴付的“三险一金”是否要并入当期工资薪金计算缴纳个人所得税")
                .searchList("三险一金超比例交了是不是要并入当期工资薪金交个税呀,超了缴付比例的三险一金是不是要并入当期工资薪金交个税,超比例缴付的三险一金是不是要并入当期工资薪金计算缴纳个人所得税,三险一金交的超了，要不要并入当期工资交个税,交超了的三险一金要不要并入当期工资交个税,超了的三险一金要并入当期工资薪金交个人所得税吗,超了比例缴付的三险一金是不是应该并入当期工资交个税,公司给我买的三险一金超了，是不是要并入我当期的工资交个税呀   - 买的三险一金超比例了，要不要并入当期工资交个税,三险一金缴付比例超了需不需要并入当期工资薪金交个人所得税").build());
        list.add(JudgeRep.builder().judgeId("75748384609140736").judgeName("对于资源税的减免税有什么规定")
                .searchList("资源税有什么减免税规定,资源税减免税规定是什么,资源税减免税规定有些什么,资源税的减免税规定有啥子,资源税减免税规定包括些什么,资源税减免税规定包括哪些规定,资源税的减免税规定有哪些规定,资源税有哪些减免税规定,资源税包括哪些减免税规定,资源税减免税有些啥子规定").build());
        list.add(JudgeRep.builder().judgeId("75745276434841600").judgeName("资源税申报期限是多久")
                .searchList("资源税的申报期限是什么,资源税的申报期限是好久,资源税的申报期限是啥子,资源税多久申报一次,资源税好久申报一次,资源税申报周期是多久,资源税申报周期是好久,资源税的申报期限是怎么规定的,资源税申报期限的具体规定是什么,资源税的申报期限的规定").build());
        list.add(JudgeRep.builder().judgeId("75745558889758720").judgeName("如何确定环境保护税的申报期限")
                .searchList("环境保护税的申报期限是什么,环境保护税的申报期限是好久,环境保护税的申报期限是啥子,环境保护税多久申报一次,环境保护税好久申报一次,环境保护税申报周期是多久,环境保护税申报周期是好久,环境保护税的申报期限是怎么规定的,环境保护税申报期限的具体规定是什么,环境保护税的申报期限的规定").build());
        list.add(JudgeRep.builder().judgeId("75749893208014848").judgeName("居民个人的综合所得如何计算")
                .searchList("居民个人的综合所得怎么计算,怎么计算居民个人的综合所得,计算居民个人综合所得,居民个人的综合所得怎么算,怎么算居民个人的综合所得,居民个人的综合所得的计算方式,综合所得怎么计算，居民个人的,居民个人的综合所得要咋个算,居民个人怎么计算综合所得,居民个人咋个算综合所得").build());
        list.add(JudgeRep.builder().judgeId("75748523240325120").judgeName("保险赔款是否征收个人所得税")
                .searchList("保险赔款交不交个税,保险的赔款要不要交个税,保险赔款应不应该交个税,保险赔款是不是要个税,保险赔款是否缴纳个税,保险赔款交不交个人所得税,保险的赔款要不要交个人所得税,保险赔款应不应该交个人所得税,保险赔款是不是要个人所得税,保险赔款是否缴纳个人所得税").build());
        list.add(JudgeRep.builder().judgeId("75748176735240192").judgeName("个人住房出租如何征收印花税")
                .searchList("个人出租住房怎么交印花税,个人住房租赁印花税怎么交,个人的住房出租怎么交印花税,个人出租住房印花税怎么交,个人的住房出租印花税怎么交,个人住房租赁怎么交印花税,我把我的住房出租，怎么交印花税,我把我的住房出租，印花税怎么交,我出租住房，税务局怎么收的印花税,我出租住房印花税税务局怎么收的").build());
        list.add(JudgeRep.builder().judgeId("75746879015485440").judgeName("印花税的减免是如何规定的")
                .searchList("印花税有什么减免税规定,印花税减免税规定是什么,印花税减免税规定有些什么,印花税的减免税规定有啥子,印花税减免税规定包括些什么,印花税减免税规定包括哪些规定,印花税的减免税规定有哪些规定,印花税有哪些减免税规定,印花税包括哪些减免税规定,印花税减免税有些啥子规定").build());
        list.add(JudgeRep.builder().judgeId("75746881941012480").judgeName("专项附加扣除什么时候开始")
                .searchList("专项附加扣除开始时间,专项附加扣除的开始时间是什么,专项附加扣除的开始时间是什么时候,专项附加扣除好久开始的呢,好久开始可以扣专项附加扣除,什么时候开始可以扣除专项附加扣除,专项附加扣除开始时间,啥子时候开始的专项附加扣除,啥子时候开始可以扣专项附加扣除,专项附加扣除的启动时间").build());
        list.add(JudgeRep.builder().judgeId("75746876160212992").judgeName("个税有哪些专项扣除")
                .searchList("个税有些什么专项扣除,个税的专项扣除有哪些,个税专项扣除有什么,个税专项扣除包括什么,个税专项扣除包括哪些,个人所得税有些什么专项扣除,个人所得税的专项扣除有哪些,个人所得税专项扣除有什么,个人所得税专项扣除包括什么,个人所得税专项扣除包括哪些").build());
        list.add(JudgeRep.builder().judgeId("75750186012377088").judgeName("资源税的征税范围包括哪些")
                .searchList("资源税的征税范围是什么,资源税的征税范围包括什么,需要交资源税的有哪些,需要交资源税的有什么,哪些需要交资源税,交资源税的范围是什么,交资源税的范围有哪些,哪些需要交资源税,收资源税的范围是什么,收资源税的范围有什么").build());
        list.add(JudgeRep.builder().judgeId("75747100297527296").judgeName("现场实名采集应提交哪些资料")
                .searchList("在现场做实名采集要交什么资料,在现场做实名采集需要什么资料,我在大厅实名采集要交什么资料,我在大厅做实名采集应该交什么资料,在大厅做实名采集提交的资料有什么,在大厅做实名采集需要提交什么资料,去到税务局做实名采集应该交什么资料,去到税务局做实名采集需要提交什么资料,去到税务局做实名采集要交的资料有什么,去到大厅做实名采集要交的资料有哪些").build());
        list.add(JudgeRep.builder().judgeId("75747117786726400").judgeName("申报环境保护税时需要提供什么资料")
                .searchList("环境保护税申报要什么资料,环境保护税申报需要什么资料,环境保护税申报要交些什么资料,环境保护税申报要提供些什么资料,环境保护税申报需要哪些资料,公司申报环保税要什么资料,单位申报环保税要哪些资料,申报环保税的时候要提交写什么资料呀,申报环保税资料,我申报环保税需要交的资料有哪些 ").build());
        list.add(JudgeRep.builder().judgeId("306977857861586945").judgeName("印花税申报")
                .searchList("电子税务局印花税如何申报,怎样在电子税务局中办理印花税的申报,印花税在电子税务局上如何进行申报操作,在网上电子税务局中要申报印花税怎么办理,在电子税务局里缴纳印花税的操作,办理印花税的缴纳在电子税务局中如何操作,在电子税务局里缴纳印花税是怎么操作的,怎样在电子税务局中办理印花税的申报,印花税在电子税务局上如何进行申报操作,在网上电子税务局中要申报印花税怎么办理,在电子税务局里缴纳印花税的操作,办理印花税的缴纳在电子税务局中如何操作,在电子税务局里缴纳印花税是怎么操作的,怎么在电子税务局申报印花税,我咋个在电子税务局里面申报印花税哦,印花税申报在电子税务局上面咋个申报,电子税务局印花税申报操作,我要在电子税务局上面申报印花税怎么操作").build());
        list.add(JudgeRep.builder().judgeId("124733365030950").judgeName("居民综合所得个人所得税年度自行申报")
                .searchList("如何做个人所得税汇算  ,个人所得税的综合所得该如何办理,个税综合所得的该如何汇算,个人所得税年度汇算怎样申报,个税汇算要咋个操作,收到短信，提醒做2020年个人所得税汇算？,个税年度汇算如何申报,我怎样申报我的个人所得税年度综合所得,如何办理年度个税综合所得申报,怎样进行个人所得税的综合所得申报,个人所得税的综合所得该如何办理,个税综合所得的该如何汇算,个人所得税年度汇算怎样申报,个税年度汇算如何申报,我怎样申报我的个人所得税年度综合所得,如何办理年度个税综合所得申报,个税汇算流程,个人所得税汇算咋个弄,个税汇算要咋个操作,我咋个申报个税汇算,咋个办理个税汇算,如何做个人所得税汇算").build());
        list.add(JudgeRep.builder().judgeId("99546742055239680").judgeName("如何确定个人出租住房需要缴纳的税种")
                .searchList("个人出租住房需要缴纳哪些税费,我的房子出租给别人需要交哪些税,我把住宅租给别人住缴税么,租房子给别人住我自己交多少税,个人住房租出去需要交什么税,个人出租住房需要缴纳哪些税费,个人出租住房要交哪些税？,我的房子出租给别人需要交哪些税,我把住宅租给别人住缴税么,租房子给别人住我自己交多少税,个人住房租出去需要交什么税,我出租住房要交哪些税哦,我出租我家咧房子有啥子税要交哦，是住宅,出租住宅交的税有啥子,出租住宅交的税有哪些,个人出租住房交的税有些啥子").build());
        list.add(JudgeRep.builder().judgeId("99546892702056448").judgeName("如何确定个人出租商铺需要缴纳的税种")
                .searchList("个人出租商铺要交哪些税,个人出租铺面需要交哪些税费,我的商铺租给别人需要缴哪些税,商铺租给别人经营需要交多少税,租铺面给别人有哪些税要交,个人商铺出租出去有什么税要交,帮我算算出租商铺要交多少税,个人出租铺面需要交哪些税费,个人出租商铺要交哪些税？,我的商铺租给别人需要缴哪些税,商铺租给别人经营需要交多少税,租铺面给别人有哪些税要交,个人商铺出租出去有什么税要交,帮我算算出租商铺要交多少税,我出租商铺要交哪些税哦,我出租我家咧房子有啥子税要交哦，是铺面,出租商铺交的税有啥子,出租商铺交的税有哪些,个人出租铺面交的税有些啥子").build());
        list.add(JudgeRep.builder().judgeId("75159848159675").judgeName("增值税专用发票（增值税税控系统）最高开票限额审批")
                .searchList("如何修改费发票限额,我要提高增值税发票开票限额该如何办理,提高发票金额的限额该怎么办,提高发票限额需要办理什么手续,我要提高增值税发票开票限额该如何办理,提高发票金额的限额该怎么办,提高发票限额需要办理什么手续,发票的限额该如何提高,专票的开票限额该怎样修改,怎么提高发票限额,发票限额怎么修改,提高发票限额要怎么做,我想提高发票限额要怎么办,发票限额怎么提高").build());
        list.add(JudgeRep.builder().judgeId("75745874224873472").judgeName("如何确认城镇土地使用税的应纳税额")
                .searchList("城镇土地使用税的应纳税额是什么,怎么算城镇土地使用税的应纳税额,怎么计算城镇土地使用税应纳税额,城镇土地使用税应纳税额怎么计算,城镇土地使用税应纳税额怎么算,城镇土地使用税怎么计算应纳税额,城镇土地使用税怎么算应纳税额,如何算城镇土地使用税的应纳税额,如何计算城镇土地使用税应纳税额,城镇土地使用税应纳税额如何计算,城镇土地使用税应纳税额如何算,城镇土地使用税如何计算应纳税额,城镇土地使用税如何算应纳税额").build());
        list.add(JudgeRep.builder().judgeId("77015244991365120").judgeName("房屋土地权属由夫妻一方所有变更为夫妻双方共有是否缴纳契税")
                .searchList("房子本来是我一个人的名字，现在改成我和我老公，交不交契税,房屋产权从夫妻一方所有改成双方共同拥有交不交契税,房子原本是一个人的，加上妻子或者丈夫的名字交不交契税,夫妻之间房子加名字交不交契税,我的房子加上我老公的名字要不要交契税,房本上是我一个人的名字，现在加上老公的名字要不要交契税,我的房子加上我老婆的名字要不要交契税,房本上是我一个人的名字，现在加上老婆的名字要不要交契税").build());
        list.add(JudgeRep.builder().judgeId("77016736387301376").judgeName("如何确定以按揭、抵押贷款方式购买房屋的契税纳税义务时间")
                .searchList("房子是按揭买的，契税什么时候交,银行贷款买房在什么时候交契税,房子是按揭买的，契税的缴纳时间是什么时候,银行贷款买房契税的缴纳时间是什么时候,贷款买房契税什么交,我按揭买的房子，在什么时候交契税,我在银行贷款买房，在什么时候交契税,我按揭买的房子，契税的缴纳时间是什么,我在银行贷款买房，契税的缴纳时间是什么,以按揭、抵押贷款方式购买房屋的契税纳税义务时间如何确定").build());
        list.add(JudgeRep.builder().judgeId("77014361767411712").judgeName("个人购买二手房时的契税税率是多少")
                .searchList("我买二手房的契税税率是好多,买的二手房的契税税率是多少,购买二手房契税税率,我买二手房的契税税率是什么,买的二手房的契税税率是什么,购买二手房契税税率是好多,买二手房的契税税率是多少,契税税率是好多买的二手房,买二手房计算契税的税率是好多,买了二手房算契税用好多税率").build());
        list.add(JudgeRep.builder().judgeId("77015939385655296").judgeName("拆迁补偿款购置房屋是否缴纳契税")
                .searchList("用拆迁赔的钱买房子交不交契税,拆迁补偿金买房子交契税吗,政府赔的拆迁款拿来买房交契税吗,拆迁补偿款买房子交不交契税,拆迁补偿款买住房交契税吗,我家房子拆了，政府赔了钱，用这个钱买房子交不交契税呀").build());
        list.add(JudgeRep.builder().judgeId("75745351499251712").judgeName("个人购买住房是否有契税优惠")
                .searchList("我要买个住宅有没有啥子优惠，关于契税的,我要买个住宅有没有契税优惠,个人购买住房有没有契税优惠,个人购买住房关于契税有没有优惠,个人购买住房契税优惠有没有,我买住房是否有契税优惠,我买住房是不是有关于契税的优惠,有没有关于契税的优惠购买住房的").build());
        list.add(JudgeRep.builder().judgeId("75748143527886848").judgeName("个人或者企业出租住房应如何计算房产税和土地使用税")
                .searchList("我出租住房房产税怎么算,单位出租住房房产税怎么算,个人出租住房怎么计算房产税,企业出租住房怎么计算房产税,个人或者单位出租住房房产税的计算方式,我出租住房土地使用税怎么算,单位出租住房土地使用税怎么算,个人出租住房怎么计算土地使用税,企业出租住房怎么计算土地使用税,个人或者单位出租住房土地使用税的计算方式  ").build());
        list.add(JudgeRep.builder().judgeId("75747006511841280").judgeName("卖东西开发票可以多开吗")
                .searchList("出售东西开发票能不能多开,卖东西开发票可以比实际的金额大吗,出售东西开发票可以比实际的金额大吗,卖东西开发票的金额可以比东西的金额大吗,出售东西开发票的金额可以比东西的金额大吗,卖东西开发票金额可以开多点吗,开的发票可以比卖的东西的金额大吗").build());
        return list;
    }

    @Builder
    static class JudgeRep {
        private String searchList;
        private String judgeId;
        private String judgeName;
    }

    public static List<String> search(List<String> searchList, String judgeId, String judgeName) {
        List<String> list = new ArrayList<>();
        for (String search : searchList) {
            String s = searchList(search, judgeId, judgeName);
            if (StringUtils.isNotEmpty(s)) {
                list.add(s);
            }
        }
        return list;
    }

    public static String searchList(String search, String judgeId, String judgeName) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("search", search);
        String forObject = RestTemplateUtils.getForObject("https://sw.noask-ai.com/portal/qa-new/hotline/es/list", paramMap);
        JSONObject jsonObject = JSONObject.parseObject(forObject);
        JSONArray results = jsonObject.getJSONArray("result");
        if (results == null || results.size() == 0) {
//            System.out.println(String.format("search:%s，无数据", search));
            return "";
        }
        Object result = results.get(0);
        JSONObject resultJson = JSONObject.parseObject(result.toString());
        String docId = resultJson.getString("docId");
        boolean isDocId = docId.equals(judgeId);
//        System.out.println(String.format("search:%s, docId:%s", search, isDocId));

        int size = results.size();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            String s = ((size - 1) < i ? "" : docName(results.get(i)));
            sb.append("\t").append(s);
        }
        return String.format("%s%s\t%s\t%s\t%s", search, sb.toString(), judgeName, (isDocId ? 0 : 1), (isDocId ? 1 : 0));
    }

    public static void search(String search, String judgeId, String judgeName) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("search", search);
        String forObject = RestTemplateUtils.getForObject("https://sw.noask-ai.com/portal/qa-new/hotline/es/list", paramMap);
        JSONObject jsonObject = JSONObject.parseObject(forObject);
        JSONObject result = jsonObject.getJSONObject("result");
        if (result == null) {
            log.info("\nsearch:{}，无数据", search);
            return;
        }
        log.info("\nsearch:{}, docId:{}", search, verify("docId", judgeId, result));
    }

    public static String docName(Object result) {
        JSONObject resultJson = JSONObject.parseObject(result.toString());
        return resultJson.getString("docName");
    }

    public static boolean verify(String key, String name, JSONObject result) {
        return result.getString(key).equals(name);
    }
}

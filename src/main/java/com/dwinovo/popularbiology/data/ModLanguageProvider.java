package com.dwinovo.popularbiology.data;

import com.dwinovo.popularbiology.PopularBiology;
import com.dwinovo.popularbiology.init.InitEntity;
import com.dwinovo.popularbiology.init.InitItems;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class ModLanguageProvider extends LanguageProvider {
    private final String locale;

    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, PopularBiology.MODID, locale);
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        if ("zh_cn".equals(locale)) {
            addCommonTranslations("Chiikawa", "背包", "跟随", "坐下", "工作");
            addJobTranslations("职业", "无", "农夫", "剑士", "弓箭手", "未知");
            addConfigTranslations(
                    "Chiikawa 配置",
                    "Chiikawa 配置",
                    "Chiikawa 配置",
                    "物品列表",
                    "记录泥土方块",
                    "魔法数字文本",
                    "魔法数字");
            addEntityTranslations("乌萨奇", "小八", "吉伊", "狮萨", "飞鼠", "栗子馒头", "獭师父");
            addItemTranslations(
                    "乌萨奇刷怪蛋",
                    "小八刷怪蛋",
                    "吉伊刷怪蛋",
                    "狮萨刷怪蛋",
                    "飞鼠刷怪蛋",
                    "栗子馒头刷怪蛋",
                    "獭师父刷怪蛋",
                    "乌萨奇的讨伐棒",
                    "小八的讨伐棒",
                    "吉伊的讨伐棒");
        } else {
            addCommonTranslations("Popular Biology", "Pet Backpack", "Follow", "Sit", "Work");
            addJobTranslations("Job", "None", "Farmer", "Fencer", "Archer", "Unknown");
            addConfigTranslations(
                    "PopularBiology Configs",
                    "PopularBiology Configs",
                    "PopularBiology Configs",
                    "Item List",
                    "Log Dirt Block",
                    "Magic Number Text",
                    "Magic Number");
            addEntityTranslations("Usagi", "Hachiware", "Chiikawa", "Shisa", "Momonga", "Kurimanju", "Rakko");
            addItemTranslations(
                    "Usagi Spawn Egg",
                    "Hachiware Spawn Egg",
                    "Chiikawa Spawn Egg",
                    "Shisa Spawn Egg",
                    "Momonga Spawn Egg",
                    "Kurimanju Spawn Egg",
                    "Rakko Spawn Egg",
                    "Usagi Weapon",
                    "Hachiware Weapon",
                    "Chiikawa Weapon");
        }
    }

    private void addCommonTranslations(String tabName, String backpackMenu, String follow, String sit, String work) {
        add("itemGroup.popularbiology", tabName);
        add("menu.popularbiology.pet_backpack", backpackMenu);
        add("message.popularbiology.pet_follow", follow + ": %s");
        add("message.popularbiology.pet_sit", sit + ": %s");
        add("message.popularbiology.pet_work", work + ": %s");
    }

    private void addJobTranslations(String jobLabel, String none, String farmer, String fencer, String archer, String unknown) {
        add("tooltip.popularbiology.pet_job", jobLabel + ": %s");
        add("tooltip.popularbiology.pet_job.none", none);
        add("tooltip.popularbiology.pet_job.farmer", farmer);
        add("tooltip.popularbiology.pet_job.fencer", fencer);
        add("tooltip.popularbiology.pet_job.archer", archer);
        add("tooltip.popularbiology.pet_job.unknown", unknown);
        add("config.jade.plugin_popularbiology.pet_job", jobLabel);
    }

    private void addConfigTranslations(String title, String section, String sectionTitle, String items,
            String logDirt, String magicIntro, String magicNumber) {
        add("popularbiology.configuration.title", title);
        add("popularbiology.configuration.section.popularbiology.common.toml", section);
        add("popularbiology.configuration.section.popularbiology.common.toml.title", sectionTitle);
        add("popularbiology.configuration.items", items);
        add("popularbiology.configuration.logDirtBlock", logDirt);
        add("popularbiology.configuration.magicNumberIntroduction", magicIntro);
        add("popularbiology.configuration.magicNumber", magicNumber);
    }

    private void addEntityTranslations(String usagi, String hachiware, String chiikawa,
            String shisa, String momonga, String kurimanju, String rakko) {
        addEntityType(InitEntity.USAGI_PET, usagi);
        addEntityType(InitEntity.HACHIWARE_PET, hachiware);
        addEntityType(InitEntity.CHIIKAWA_PET, chiikawa);
        addEntityType(InitEntity.SHISA_PET, shisa);
        addEntityType(InitEntity.MOMONGA_PET, momonga);
        addEntityType(InitEntity.KURIMANJU_PET, kurimanju);
        addEntityType(InitEntity.RAKKO_PET, rakko);
    }

    private void addItemTranslations(String usagiEgg, String hachiwareEgg, String chiikawaEgg,
            String shisaEgg, String momongaEgg, String kurimanjuEgg, String rakkoEgg,
            String usagiWeapon, String hachiwareWeapon, String chiikawaWeapon) {
        addItem(InitItems.USAGI_SPAWN_EGG, usagiEgg);
        addItem(InitItems.HACHIWARE_SPAWN_EGG, hachiwareEgg);
        addItem(InitItems.CHIIKAWA_SPAWN_EGG, chiikawaEgg);
        addItem(InitItems.SHISA_SPAWN_EGG, shisaEgg);
        addItem(InitItems.MOMONGA_SPAWN_EGG, momongaEgg);
        addItem(InitItems.KURIMANJU_SPAWN_EGG, kurimanjuEgg);
        addItem(InitItems.RAKKO_SPAWN_EGG, rakkoEgg);
        addItem(InitItems.USAGI_WEAPON, usagiWeapon);
        addItem(InitItems.HACHIWARE_WEAPON, hachiwareWeapon);
        addItem(InitItems.CHIIKAWA_WEAPON, chiikawaWeapon);
    }
}

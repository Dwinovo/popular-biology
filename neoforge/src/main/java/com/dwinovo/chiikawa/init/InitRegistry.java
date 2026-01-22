package com.dwinovo.chiikawa.init;

import com.dwinovo.chiikawa.Chiikawa;
import com.dwinovo.chiikawa.entity.brain.handler.ArcherJobHandler;
import com.dwinovo.chiikawa.entity.brain.handler.FarmerJobHandler;
import com.dwinovo.chiikawa.entity.brain.handler.FencerJobHandler;
import com.dwinovo.chiikawa.entity.brain.handler.NoneJobHandler;
import com.dwinovo.chiikawa.entity.job.api.IPetJob;
import com.dwinovo.chiikawa.entity.job.impl.BasicJob;
import com.dwinovo.chiikawa.entity.job.impl.NoneJob;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import com.dwinovo.chiikawa.init.InitTag;

public final class InitRegistry {
    public static final int NONE_ID = 0;
    public static final int FARMER_ID = 1;
    public static final int FENCER_ID = 2;
    public static final int ARCHER_ID = 3;

    public static final ResourceKey<Registry<IPetJob>> PET_JOB_KEY = ResourceKey.createRegistryKey(
        ResourceLocation.fromNamespaceAndPath(Chiikawa.MODID, "pet_jobs")
    );

    public static final Registry<IPetJob> PET_JOB_REGISTRY = new RegistryBuilder<>(PET_JOB_KEY)
        .sync(true)
        .defaultKey(ResourceLocation.fromNamespaceAndPath(Chiikawa.MODID, "none"))
        .create();

    public static final DeferredRegister<IPetJob> PET_JOBS = DeferredRegister.create(PET_JOB_REGISTRY, Chiikawa.MODID);

    public static final Supplier<IPetJob> NONE = PET_JOBS.register(
        "none",
        () -> new NoneJob(NONE_ID)
    );
    public static final Supplier<IPetJob> FARMER = PET_JOBS.register(
        "farmer",
        () -> new BasicJob(
            FARMER_ID,
            10,
            InitTag.ENTITY_FARMER_TOOLS,
            FarmerJobHandler::initBrain,
            FarmerJobHandler::tickBrain
        )
    );
    public static final Supplier<IPetJob> FENCER = PET_JOBS.register(
        "fencer",
        () -> new BasicJob(
            FENCER_ID,
            10,
            InitTag.ENTITY_FENCER_TOOLS,
            FencerJobHandler::initBrain,
            FencerJobHandler::tickBrain
        )
    );
    public static final Supplier<IPetJob> ARCHER = PET_JOBS.register(
        "archer",
        () -> new BasicJob(
            ARCHER_ID,
            10,
            InitTag.ENTITY_ARCHER_TOOLS,
            ArcherJobHandler::initBrain,
            ArcherJobHandler::tickBrain
        )
    );

    private InitRegistry() {
    }

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(InitRegistry::registerNewRegistries);
        PET_JOBS.register(modEventBus);
    }

    private static void registerNewRegistries(NewRegistryEvent event) {
        event.register(PET_JOB_REGISTRY);
    }

    public static IPetJob getJobFromId(int id) {
        if (id == FARMER_ID) {
            return FARMER.get();
        }
        if (id == FENCER_ID) {
            return FENCER.get();
        }
        if (id == ARCHER_ID) {
            return ARCHER.get();
        }
        return NONE.get();
    }

    public static int getIdFromJob(IPetJob job) {
        if (job == null) {
            return NONE_ID;
        }
        return job.getId();
    }
}



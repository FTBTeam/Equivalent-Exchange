package net.creeperhost.equivalentexchange.dynemc.json;

import com.google.common.base.Stopwatch;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.dynemc.DynamicEmc;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.Reader;
import java.util.List;
import java.util.Map;

public class ProjecteEmcMapper
{
    public static void init(ResourceManager resourceManager)
    {
        String folderName = "pe_custom_conversions";
        String fileType = ".json";

        EquivalentExchange.LOGGER.info("ProjectE EmcMapper loaded");
        Stopwatch stopwatch = Stopwatch.createStarted();

        for (Map.Entry<ResourceLocation, List<Resource>> entry : resourceManager.listResourceStacks(folderName, n -> n.getPath().endsWith(fileType)).entrySet())
        {
            ResourceLocation resourceLocation = entry.getKey();
            EquivalentExchange.LOGGER.info("found file {}", resourceLocation);

            try
            {
                for(Resource resource : entry.getValue())
                {
                    try
                    {
                        Reader reader = resource.openAsReader();
                        JsonElement json = JsonParser.parseReader(reader);
                        JsonObject object = json.getAsJsonObject().getAsJsonObject("values").getAsJsonObject("before");
                        if(object == null) return;

                        object.entrySet().forEach(stringJsonElementEntry ->
                        {
                            String key = stringJsonElementEntry.getKey();
                            double value = stringJsonElementEntry.getValue().getAsDouble();
                            if(key.startsWith("#"))
                            {
                                DynamicEmc.generateForTag(key.substring(1), value);
                                EquivalentExchange.LOGGER.info("Registering tag {} with value {}", key, value);
                            }
                            else
                            {
                                DynamicEmc.generateForItem(key, value);
                                EquivalentExchange.LOGGER.info("Registering item {} with value {}", key, value);
                            }
                        });
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        EquivalentExchange.LOGGER.info("Finished loading ProjectE custom conversions jsons after " + stopwatch.elapsed().toMillis() + "ms");
        stopwatch.stop();
    }
}

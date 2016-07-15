package com.griddynamics.qa.datapool;

import com.griddynamics.qa.datapool.datatype.IDataType;
import com.griddynamics.qa.datapool.datatype.IDataTypeManager;
import com.griddynamics.qa.datapool.serialization.Marshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * //TODO: L.E., Need to provide JavaDoc.
 *
 */
public class DataPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataPool.class);
    private static final DataCollection FETCHED_DATA = new DataCollection();
    private static final Map<Class<? extends IDataType>, IDataTypeManager<? extends IDataType>> MANAGERS = new HashMap<>();
    private static final Map<String, Class<? extends IDataType>> DATA_TYPES = new HashMap<>();
    private static final String DEFAULT_DATAPOOL_FILENAME = "./datapool.yml";

    private DataPool() {}

    /**
     * Adds the data-type manager instance to the set of known managers.
     */
    public static void registerManagerForDataType(IDataTypeManager<? extends IDataType> newManager,
                                                    Class<? extends  IDataType> dtClass) {
        MANAGERS.put(dtClass, newManager);
        DATA_TYPES.put(dtClass.getSimpleName(), dtClass);
    }

    /**
     * Iterates over all known (registered) data-type managers and gathers data fetched.
     */
    public static void fetch() {
        for (Map.Entry<Class<? extends IDataType>, IDataTypeManager<? extends IDataType>> entry : MANAGERS.entrySet()) {
            try {
                FETCHED_DATA.put(entry.getKey(), entry.getValue().fetch());
            } catch (Exception e) {
                LOGGER.error("Could not fetch data for {} using {} due to ",
                                entry.getKey().getSimpleName(),
                                entry.getValue().getClass().getSimpleName(),
                                e);
            }
        }
    }

    public static <T extends IDataType> List<T> find(String dtClassName, FilterCriterion...criteria){
        Class<T> dtClass = getDataTypeClassByName(dtClassName);
        if(dtClass == null){
            throw new DataPoolException("There is no data type in pool that corresponds to name %s", dtClassName);
        }
        return find(dtClass, criteria);
    }


    /**
     * The method is used to find data subset you are interested in for specific data-type.
     *
     * @param dtClass - data-type class
     * @param criteria - zero or more filtering criteria. If none is provided non-filtered collection is returned.
     * @param <T> the actual data-type
     * @return filtered collection.
     *  If there is no data which corresponds to your criteria then empty collection is returned.
     *  If you are requested data for non-existent key then null will be returned.
     */
    @SuppressWarnings("unchecked")
    public static <T extends IDataType> List<T> find(Class<T> dtClass, FilterCriterion...criteria){
        List<T> fullList = (List<T>) FETCHED_DATA.get(dtClass);
        return filter(fullList, criteria);
    }

    /**
     * Serializes (to Yaml) DataPool data into default filename
     */
    public static void store(){
        store(DEFAULT_DATAPOOL_FILENAME);
    }

    /**
     * Serializes (to Yaml) DataPool data into given filename
     * @param filename
     */
    public static void store(String filename){
        Yaml yaml = Marshaller.getYamlToStore();
        try{
            File datapoolFile = new File(filename);
            BufferedWriter bw = new BufferedWriter(new FileWriter(datapoolFile));
            LOGGER.info("Writing fetched data to {}", datapoolFile.getAbsolutePath());
            bw.write(yaml.dump(FETCHED_DATA));
            bw.close();
        } catch (IOException e) {
            throw new DataPoolException(e);
        }
    }

    /**
     * Deserialize (from Yaml) DataPool from default filename
     */
    @SuppressWarnings("unchecked")
    public static void load(){
        //TODO: use default for the moment. Should be able to override with properties
        load(Stream.of(DEFAULT_DATAPOOL_FILENAME).map(Paths::get).toArray(Path[]::new));
    }

    /**
     * Deserialize (from Yaml) DataPool from provided paths
     * @param paths
     */
    @SuppressWarnings("unchecked")
    public static void load(Path...paths){
        LinkedHashMap<Class<? extends IDataType>, List<? extends IDataType>> linkedMap = new LinkedHashMap<>();
        for(Path path : paths){
            try(BufferedReader reader = Files.newBufferedReader(path)){
                Yaml yaml = Marshaller.getYamlToLoad();
                linkedMap.putAll((LinkedHashMap<Class<? extends IDataType>, List<? extends IDataType>>)yaml.load(reader));
            } catch (IOException exc) {
                LOGGER.error("IOException occurs while loading {}", path);
                LOGGER.error(exc.toString());
            }
        }
        FETCHED_DATA.clear();
        FETCHED_DATA.putAll(linkedMap);
    }

    @SuppressWarnings("unchecked")
    private static <T extends IDataType> Class<T> getDataTypeClassByName(String name){
        return (Class<T>)DATA_TYPES.get(name);
    }

    private static <T extends IDataType> List<T> filter(List<T> fullList, FilterCriterion...criteria){
        if(fullList == null) { return null; }
        List<T> filteredList = new LinkedList<>(fullList);
        for(FilterCriterion criterion : criteria){
            filteredList = filteredList
                .stream()
                .filter(criterion.getPredicate())
                .collect(Collectors.toList());
        }
        return filteredList;
    }
}

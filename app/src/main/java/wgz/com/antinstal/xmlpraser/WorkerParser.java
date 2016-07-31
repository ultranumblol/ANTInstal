package wgz.com.antinstal.xmlpraser;



import java.io.InputStream;
import java.util.List;

import wgz.com.antinstal.bean.Worker;

/**
 * Created by qwerr on 2015/12/23.
 */
public interface WorkerParser {
    public List<Worker> parse(InputStream is) throws Exception;

}

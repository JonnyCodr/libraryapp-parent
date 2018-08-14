package commontests.utils;

import org.junit.Ignore;

@Ignore
public interface DbCommand<T> {

    T execute();
}

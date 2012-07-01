package example;

import java.lang.reflect.Method;
import java.util.List;

import com.google.common.collect.Iterables;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author amir.raminfar
 */
public final class Loops {
    private Loops() {
    }

    public static <T> T all(final Iterable<T> list) {
        T first = Iterables.getFirst(list, null);

        if (first != null) {
            final Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(first.getClass());
            MethodInterceptor methodInterceptor = new MethodInterceptor() {
                public Object intercept(Object proxy, final Method method, final Object[] args, MethodProxy methodProxy)
                throws Throwable {
                    List<Object> output = newArrayList();
                    for (T t : list) {
                        output.add(methodProxy.invoke(t, args));
                    }
                    return Iterables.getLast(output);
                }
            };

            enhancer.setCallbackType(methodInterceptor.getClass());

            Class mockClass = enhancer.createClass();

            Enhancer.registerCallbacks(mockClass, new Callback[]{methodInterceptor});

            Objenesis objenesis = new ObjenesisStd();

            @SuppressWarnings("unchecked")
            T t = (T) objenesis.newInstance(mockClass);

            return t;
        }
        else {
            throw new IllegalArgumentException("List cannot be empty");
        }
    }
}

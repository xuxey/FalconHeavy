package com.xuxe.falconHeavy.commands.admin.eval;

import com.xuxe.falconHeavy.FalconHeavy;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;

import javax.script.ScriptEngine;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Shamelessly borrowed from HotLava: https://github.com/HotLava03/Baclava
 * who borrowed it from https://github.com/Almighty-Alpaca/JDA-Butler/blob/master/bot/src/main/java/com/almightyalpaca/discord/jdabutler/eval/Engine.java
 * The code in this file does not belong to me, and is used under the pretext of the liberal Apache 2.0 license
 **/
public class EvalComponent {

    // Imports used
    public static List<String> DEFAULT_IMPORTS = new ArrayList<>();

    private final static ScheduledExecutorService service = Executors.newScheduledThreadPool(1, newThreadFactory());

    private static ThreadFactory newThreadFactory() {
        return (r) ->
        {
            Thread t = new Thread(r, "eval-thread");
            t.setDaemon(false);
            t.setUncaughtExceptionHandler((final Thread thread, final Throwable throwable) ->
                    FalconHeavy.logger.error("There was a uncaught exception in the {} threadpool", thread.getName(), throwable));
            return t;
        };
    }

    /**
     * @param fields       All shortcut variables to be used in the code
     * @param classImports All class imports
     * @param script       The script to run
     * @return The result in a Triple container. The first parameter is the result, the second is the output and the third an error/exception.
     */
    Triple<Object, String, String> eval(final Map<String, Object> fields, final Collection<String> classImports, final String script) {

        StringBuilder importString = new StringBuilder();
        for (final String s : classImports)
            importString.append("import ").append(s).append(";");
        for (final String s : EvalComponent.DEFAULT_IMPORTS)
            importString.append("import ").append(s).append(".*;");
        return this.eval(fields, importString + script, new GroovyScriptEngineImpl());
    }

    private Triple<Object, String, String> eval(final Map<String, Object> fields, final String script, final ScriptEngine engine) {

        for (final Map.Entry<String, Object> shortcut : fields.entrySet())
            engine.put(shortcut.getKey(), shortcut.getValue());

        final StringWriter outString = new StringWriter();
        final PrintWriter outWriter = new PrintWriter(outString);
        engine.getContext().setWriter(outWriter);

        final StringWriter errorString = new StringWriter();
        final PrintWriter errorWriter = new PrintWriter(errorString);
        engine.getContext().setErrorWriter(errorWriter);
        Object result = null;
        final ScheduledFuture<Object> future = EvalComponent.service.schedule(() -> engine.eval(script), 0, TimeUnit.MILLISECONDS);

        try {
            result = future.get(2, TimeUnit.SECONDS);
        } catch (final ExecutionException e) {
            errorWriter.println(e.getCause().toString());
        } catch (TimeoutException | InterruptedException e) {
            future.cancel(true);
            errorWriter.println(e.toString());
        }

        return new ImmutableTriple<>(result, outString.toString(), errorString.toString());
    }
}

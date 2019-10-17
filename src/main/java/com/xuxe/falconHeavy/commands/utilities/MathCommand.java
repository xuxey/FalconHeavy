package com.xuxe.falconHeavy.commands.utilities;

import com.xuxe.falconHeavy.commands.Category;
import com.xuxe.falconHeavy.framework.command.Command;
import com.xuxe.falconHeavy.framework.triggers.CommandTrigger;
import com.xuxe.falconHeavy.utils.IncorrectSyntaxException;
import org.mariuszgromada.math.mxparser.Expression;

import java.text.DecimalFormat;

public class MathCommand extends Command {
    public MathCommand() {
        this.name = "math";
        this.aliases = new String[]{"calculate", "calc"};
        this.category = Category.Utilities;
        this.help = "Calculates a math expression";
        this.syntax = "math <expression>";
    }

    @Override
    public void run(CommandTrigger trigger) throws IncorrectSyntaxException {
        String expression = trigger.getString();
        Expression exp = new Expression(expression);
        double result = exp.calculate();
        DecimalFormat format = new DecimalFormat("###.##");
        String res = format.format(result);
        if (res.equalsIgnoreCase("NaN") || res.equalsIgnoreCase("Infinity")) {
            trigger.respond("" + result);
        } else {
            double val = Double.parseDouble(res);
            if (val - (int) val == 0) {
                trigger.respond(val + "");
            } else {
                trigger.respond(format.format(val));
            }
        }

    }
}

package org.firstinspires.ftc.teamcode.misc;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.reflect.Field;

public class PlaceholderReporter {
    public static void reportPlaceholderFields(Telemetry telemetry, Object... targets) {
        for (Object target: targets) {
            Class<?> clazz = target.getClass();
            while (clazz != null) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Placeholder.class)) {
                        field.setAccessible(true); // needed if field is private
                        try {
                            Object value = field.get(target);
                            telemetry.addData("<b>WARNING</b>", clazz.getSimpleName() + "." + field.getName() + " is marked as a placeholder");
                        } catch (IllegalAccessException e) {
                            telemetry.addData(field.getName(), "ERR");
                        }
                    }
                }
                clazz = clazz.getSuperclass();
            }
        }
    }
}

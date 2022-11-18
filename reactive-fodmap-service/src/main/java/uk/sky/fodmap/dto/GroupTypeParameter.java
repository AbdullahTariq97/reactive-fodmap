package uk.sky.fodmap.dto;

import org.springframework.boot.actuate.endpoint.invoke.OperationParameter;

public class GroupTypeParameter implements OperationParameter {
    @Override
    public String getName() {
        return "group type";
    }

    @Override
    public Class<?> getType() {
        return String.class;
    }

    @Override
    public boolean isMandatory() {
        return true;
    }

    @Override
    public String toString() {
        return "group type";
    }
}

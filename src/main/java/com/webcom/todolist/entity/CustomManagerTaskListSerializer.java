package com.webcom.todolist.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

class CustomManagerTaskListSerializer extends StdSerializer<List<Task>> {

    public CustomManagerTaskListSerializer() {
        this(null);
    }

    public CustomManagerTaskListSerializer(Class<List<Task>> t) {
        super(t);
    }

    @Override
    public void serialize(List<Task> items, JsonGenerator generator, SerializerProvider provider) throws IOException {
        List<Long> ids = items.stream().map(Task::getTaskId).collect(toList());
        generator.writeObject(ids);
    }
}

package net.pd.ethraa.integration.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import net.pd.ethraa.common.model.Group;

//@Component
public class GroupDeserializer extends JsonDeserializer<Group> {

    /**
     *
     */
    private static final long serialVersionUID = 2714826346816335437L;

    @Override
    public Group deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

	String groupID = jp.readValueAsTree().toString();

	return new Group(Long.valueOf(groupID));
    }

}

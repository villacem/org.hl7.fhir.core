package org.hl7.fhir.r5.formats;

/*-
 * #%L
 * org.hl7.fhir.r5
 * %%
 * Copyright (C) 2014 - 2019 Health Level 7
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class JsonCreatorCanonical implements JsonCreator {

  public class JsonCanValue {
    String name;
    private JsonCanValue(String name) {
      this.name = name;  
   }
  }

  private class JsonCanNumberValue extends JsonCanValue {
    private BigDecimal value;
    private JsonCanNumberValue(String name, BigDecimal value) {
      super(name);
      this.value = value;  
    }
  }

  private class JsonCanPresentedNumberValue extends JsonCanValue {
    private String value;
    private JsonCanPresentedNumberValue(String name, String value) {
      super(name);
      this.value = value;  
    }
  }

  private class JsonCanIntegerValue extends JsonCanValue {
    private Integer value;
    private JsonCanIntegerValue(String name, Integer value) {
      super(name);
      this.value = value;  
    }
  }

  private class JsonCanBooleanValue extends JsonCanValue  {
    private Boolean value;
    private JsonCanBooleanValue(String name, Boolean value) {
      super(name);
      this.value = value;  
    }
  }

  private class JsonCanStringValue extends JsonCanValue {
    private String value;
    private JsonCanStringValue(String name, String value) {
      super(name);
      this.value = value;  
    }
  }

  private class JsonCanNullValue extends JsonCanValue  {
    private JsonCanNullValue(String name) {
      super(name);
    }
  }

  public class JsonCanObject extends JsonCanValue {

    boolean array;
    List<JsonCanValue> children = new ArrayList<JsonCanValue>();
    
    public JsonCanObject(String name, boolean array) {
      super(name);
      this.array = array;
    }

    public void addProp(JsonCanValue obj) {
      children.add(obj);
    }
  }

  Stack<JsonCanObject> stack;
  JsonCanObject root; 
  JsonCreatorDirect jj;
  String name;
  
  public JsonCreatorCanonical(OutputStreamWriter osw) {
    stack = new Stack<JsonCreatorCanonical.JsonCanObject>();
    jj = new JsonCreatorDirect(osw);
    name = null;
  }

  private String takeName() {
    String res = name;
    name = null;
    return res;
  }
  
  @Override
  public void setIndent(String indent) {
    if (!indent.equals(""))
      throw new Error("do not use pretty when canonical is set");
    jj.setIndent(indent);
  }

  @Override
  public void beginObject() throws IOException {
    JsonCanObject obj = new JsonCanObject(takeName(), false);
    if (stack.isEmpty())
      root = obj;
    else
      stack.peek().addProp(obj);
    stack.push(obj);
  }

  @Override
  public void endObject() throws IOException {
    stack.pop();
  }

  @Override
  public void nullValue() throws IOException {
    stack.peek().addProp(new JsonCanNullValue(takeName()));
  }

  @Override
  public void name(String name) throws IOException {
    this.name = name;
  }

  @Override
  public void value(String value) throws IOException {
    stack.peek().addProp(new JsonCanStringValue(takeName(), value));    
  }

  @Override
  public void value(Boolean value) throws IOException {
    stack.peek().addProp(new JsonCanBooleanValue(takeName(), value));    
  }

  @Override
  public void value(BigDecimal value) throws IOException {
    stack.peek().addProp(new JsonCanNumberValue(takeName(), value));    
  }
  @Override
  public void valueNum(String value) throws IOException {
    stack.peek().addProp(new JsonCanPresentedNumberValue(takeName(), value));    
  }


  @Override
  public void value(Integer value) throws IOException {
    stack.peek().addProp(new JsonCanIntegerValue(takeName(), value));    
  }

  @Override
  public void beginArray() throws IOException {
    JsonCanObject obj = new JsonCanObject(takeName(), true);
    if (!stack.isEmpty())
      stack.peek().addProp(obj);
    stack.push(obj);
    
  }

  @Override
  public void endArray() throws IOException {
    stack.pop();    
  }

  @Override
  public void finish() throws IOException {
    writeObject(root);
  }

  private void writeObject(JsonCanObject obj) throws IOException {
    jj.beginObject();
    List<String> names = new ArrayList<String>();
    for (JsonCanValue v : obj.children) 
      names.add(v.name);
    Collections.sort(names);
    for (String n : names) {
      jj.name(n);
      JsonCanValue v = getPropForName(n, obj.children);
      if (v instanceof JsonCanNumberValue)
        jj.value(((JsonCanNumberValue) v).value);
      else if (v instanceof JsonCanPresentedNumberValue)
        jj.valueNum(((JsonCanPresentedNumberValue) v).value);
      else if (v instanceof JsonCanIntegerValue)
        jj.value(((JsonCanIntegerValue) v).value);
      else if (v instanceof JsonCanBooleanValue)
        jj.value(((JsonCanBooleanValue) v).value);
      else if (v instanceof JsonCanStringValue)
        jj.value(((JsonCanStringValue) v).value);
      else if (v instanceof JsonCanNullValue)
        jj.nullValue();
      else if (v instanceof JsonCanObject) {
        JsonCanObject o = (JsonCanObject) v;
        if (o.array) 
          writeArray(o);
        else
          writeObject(o);
      } else
        throw new Error("not possible");
    }
    jj.endObject();
  }

  private JsonCanValue getPropForName(String name, List<JsonCanValue> children) {
    for (JsonCanValue child : children)
      if (child.name.equals(name))
        return child;
    return null;
  }

  private void writeArray(JsonCanObject arr) throws IOException {
    jj.beginArray();
    for (JsonCanValue v : arr.children) { 
      if (v instanceof JsonCanNumberValue)
        jj.value(((JsonCanNumberValue) v).value);
      else if (v instanceof JsonCanIntegerValue)
          jj.value(((JsonCanIntegerValue) v).value);
      else if (v instanceof JsonCanBooleanValue)
        jj.value(((JsonCanBooleanValue) v).value);
      else if (v instanceof JsonCanStringValue)
        jj.value(((JsonCanStringValue) v).value);
      else if (v instanceof JsonCanNullValue)
        jj.nullValue();
      else if (v instanceof JsonCanObject) {
        JsonCanObject o = (JsonCanObject) v;
        if (o.array) 
          writeArray(o);
        else
          writeObject(o);
      } else
        throw new Error("not possible");
    }
    jj.endArray();    
  }

  @Override
  public void link(String href) {
    // not used
  }
       
  @Override
  public void anchor(String name) {
    // not used
  }
       
    
}

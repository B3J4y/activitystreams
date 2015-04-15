/**
 * Copyright 2013 OpenSocial Foundation
 * Copyright 2013 International Business Machines Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Utility library for working with Activity Streams Actions
 * Requires underscorejs.
 *
 * @author James M Snell (jasnell@us.ibm.com)
 */
package com.ibm.common.activitystreams.actions;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Iterables.size;

import java.io.ObjectStreamException;
import java.io.Serializable;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;

/**
 */
public class EmbedActionHandler 
  extends ActionHandler
  implements Serializable {
  
  /**
   * Method makeEmbedActionHandler.
   * @return Builder
   */
  public static Builder makeEmbedActionHandler() {
    return new Builder();
  }
  
  /**
   */
  public static final class Builder 
    extends AbstractBuilder<EmbedActionHandler, Builder> {

    public Builder() {
      super("EmbedActionHandler");
    }
    
    /**
     * Method getActual.
     * @return EmbedActionHandler
     */
    protected EmbedActionHandler getActual() {
      return new EmbedActionHandler(this);
    }
    
  }
  
  /**
   */
  @SuppressWarnings("unchecked")
  public static abstract class AbstractBuilder<A extends EmbedActionHandler, B extends AbstractBuilder<A,B>>
    extends ActionHandler.Builder<A,B> {
 
    private boolean styleset = false;
    private static ImmutableList.Builder<StylesValue> styles = 
      ImmutableList.builder();
   
    /**
     * Constructor for AbstractBuilder.
     * @param objectType String
     */
    protected AbstractBuilder(String objectType) {
      objectType(objectType);
    }
    
    /**
     * Method target.
     * @param target String
     * @return B
     */
    public B target(String target) {
      set("target", target);
      return (B)this;
    }
    
    /**
     * Method style.
     * @param style StylesValue
     * @return B
     */
    public B style(StylesValue style) {
      styleset = true;
      styles.add(style);
      return (B)this;
    }
    
    /**
     * Method style.
     * @param style Supplier&lt;StylesValue&gt;
     * @return B
     */
    public B style(Supplier<StylesValue> style) {
      return style(style.get());
    }
    
    /**
     * Method style.
     * @param styles StylesValue[]
     * @return B
     */
    public B style(StylesValue... styles) {
      for (StylesValue s : styles) 
        style(s);
      return (B)this;
    }
    
    /**
     * Method style.
     * @param styles Supplier&lt;StylesValue&gt;[]
     * @return B
     */
    public B style(Supplier<StylesValue>... styles) {
      for (Supplier<StylesValue> s : styles)
        style(s);
      return (B)this;
    }
      
    /**
     * Method actualGet.
     * @return A
     */
    protected final A actualGet() {
      if (styleset) {
        ImmutableList<StylesValue> val = styles.build();
        if (size(val) == 1) {
          set("style", getFirst(val,null));
        } else {
          set("style", val);
        }
      }
      return getActual();
    }
  
    /**
     * Method getActual.
     * @return A
     */
    protected abstract A getActual();
  }
  
  /**
   * Constructor for EmbedActionHandler.
   * @param builder AbstractBuilder&lt;?,?&gt;
   */
  protected EmbedActionHandler(AbstractBuilder<?,?> builder) {
    super(builder);
  }

  /**
   * Method styles.
   * @return Iterable&lt;StylesValue&gt;
   */
  @SuppressWarnings("unchecked")
  public Iterable<StylesValue> styles() {
    Object styles = get("style");
    if (styles instanceof StylesValue)
      return ImmutableList.<StylesValue>of((StylesValue)styles);
    else if (styles instanceof Iterable)
      return (Iterable<StylesValue>)styles;
    else return ImmutableList.of();
  }
  
  /**
   * Method styles.
   * @param media String
   * @return Iterable&lt;StylesValue&gt;
   */
  public Iterable<StylesValue> styles(final String media) {
    checkNotNull(media);
    return styles(new Predicate<StylesValue>() {
      public boolean apply(StylesValue style) {
        return media.equalsIgnoreCase(style.media());
      }
    });
  }
  
  /**
   * Method styles.
   * @param matcher Predicate&lt;StylesValue&gt;
   * @return Iterable&lt;StylesValue&gt;
   */
  public Iterable<StylesValue> styles(Predicate<StylesValue> matcher) {
    return filter(styles(), matcher);
  }
  
  /**
   * Method target.
   * @return String
   */
  public String target() {
    return getString(
      "target", 
      ActionMakers.TARGET_DEFAULT);
  }
  
  Object writeReplace() throws java.io.ObjectStreamException {
    return new SerializedForm(this);
  }
  
  private static class SerializedForm 
    extends AbstractSerializedForm<EmbedActionHandler> {
    private static final long serialVersionUID = -2060301713159936285L;
    protected SerializedForm(EmbedActionHandler obj) {
      super(obj);
    }
    Object readResolve() throws ObjectStreamException {
      return super.doReadResolve();
    }
    protected EmbedActionHandler.Builder builder() {
      return ActionMakers.embedAction();
    }
  }
}

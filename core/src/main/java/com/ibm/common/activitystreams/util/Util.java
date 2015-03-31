package com.ibm.common.activitystreams.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Locale;

import com.google.common.base.Optional;
import com.ibm.common.activitystreams.ASObject;
import com.ibm.common.activitystreams.LinkValue;
import com.ibm.common.activitystreams.ValueType;
import com.ibm.common.activitystreams.LinkValue.SimpleLinkValue;
public final class Util {

	private Util() {}

	/**
	 * Method convLocale.
	 * @param locale Locale
	 * @return String 
	 **/
	public static String convLocale(Locale locale) {
		return checkNotNull(locale).toString().replaceAll("_", "-");
	}

	public static final String DEFAULT_LOCALE = convLocale(Locale.getDefault());

	public static final Optional<String> getIdFrom(LinkValue linkValue) {
		String id = null;

		if (linkValue != null) {
			switch (linkValue.valueType()) {
			case SIMPLE:
				id =  ((SimpleLinkValue)linkValue).url();
				break;
			case OBJECT:
				id =  ((ASObject)linkValue).id();
				break;
			default:
				break;
			}
		}
		return Optional.fromNullable("".equals(id) ? null : id); // empty string is considered as a null value
	}
}

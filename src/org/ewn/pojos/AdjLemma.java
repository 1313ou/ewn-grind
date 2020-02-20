package org.ewn.pojos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Adjective Lemma (suffix-stripped)
 *
 * @author Bernard Bou
 */
public class AdjLemma extends Lemma
{
	private static final String REGEX = "\\((a|p|ip)\\)";

	private static final Pattern PATTERN = Pattern.compile(REGEX);

	protected final AdjPosition adjPosition;

	/**
	 * Constructor
	 *
	 * @param normString
	 *            normalized string
	 * @throws ParsePojoException
	 */
	private AdjLemma(final BareNormalizedString normString, final AdjPosition adjPosition) throws ParsePojoException
	{
		super(normString);
		this.adjPosition = adjPosition;
	}

	/**
	 * Constructor
	 *
	 * @param normString
	 *            normalized string
	 * @throws ParsePojoException
	 */
	public static Lemma makeAdj(NormalizedString normalizedString)
	{
		// trailing adjective position

		final Matcher matcher = PATTERN.matcher(normalizedString.toString());
		if (matcher.find())
		{
			try
			{
				// parse position
				AdjPosition adjPosition = AdjPosition.parseAdjPosition(matcher.group());

				// strip position
				return new AdjLemma(new BareNormalizedString(normalizedString), adjPosition);
			}
			catch (ParsePojoException e)
			{
				//
			}
		}
		return Lemma.make(normalizedString);
	}

	/**
	 * Get position
	 *
	 * @return position
	 */
	public AdjPosition getPosition()
	{
		return this.adjPosition;
	}

	/**
	 * Get position tag (to append to lemma)
	 *
	 * @return position string
	 */
	public String toPositionSuffix()
	{
		if (this.adjPosition == null)
			return "";
		return "(" + this.adjPosition.getTag() + ")";
	}

	@Override
	public boolean equals(final Object obj)
	{
		// ignore position
		return super.equals(obj);
	}

	@SuppressWarnings("EmptyMethod")
	@Override
	public int hashCode()
	{
		// ignore position
		return super.hashCode();
	}

}

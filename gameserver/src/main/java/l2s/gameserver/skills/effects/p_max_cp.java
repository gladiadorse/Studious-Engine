package l2s.gameserver.skills.effects;

import l2s.gameserver.model.actor.instances.creature.Abnormal;
import l2s.gameserver.stats.Env;
import l2s.gameserver.stats.StatModifierType;
import l2s.gameserver.stats.Stats;
import l2s.gameserver.templates.skill.EffectTemplate;

public final class p_max_cp extends p_abstract_stat_effect
{
	private final boolean _restore;

	public p_max_cp(Abnormal abnormal, Env env, EffectTemplate template)
	{
		super(abnormal, env, template, Stats.MAX_CP);
		_restore = template.getParam().getBool("restore", false);
	}

	@Override
	protected void afterApplyActions()
	{
		if(!_restore || getEffected().isHealBlocked())
			return;

		double power = getValue();
		if(getModifierType() == StatModifierType.PER)
			power = power / 100. * getEffected().getMaxCp();

		if(power > 0)
			getEffected().setCurrentCp(getEffected().getCurrentCp() + power, false);
	}
}
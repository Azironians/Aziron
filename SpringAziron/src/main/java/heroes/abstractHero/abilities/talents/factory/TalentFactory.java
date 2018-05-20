package heroes.abstractHero.abilities.talents.factory;

import heroes.abstractHero.abilities.talents.skill.Skill;

import java.util.List;

public interface TalentFactory {

    Skill getSwapSkill();

    List<Skill> getSuperSkills();
}

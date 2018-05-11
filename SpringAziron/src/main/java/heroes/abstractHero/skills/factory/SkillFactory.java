package heroes.abstractHero.skills.factory;

import heroes.abstractHero.skills.ASkill;

import java.util.List;

public interface SkillFactory {

    ASkill getSwapSkill();

    List<ASkill> getSuperSkills();
}

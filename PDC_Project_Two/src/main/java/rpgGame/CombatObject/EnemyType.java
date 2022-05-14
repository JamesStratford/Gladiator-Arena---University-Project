package rpgGame.CombatObject;

/**
 *
 * @author James Stratford 21129223
 */
public enum EnemyType
{
    HUMAN("Human"),
    GOBLIN("Goblin"),
    ORC("Orc"),
    ELF("Elf"),
    UNDEAD("Undead");
    
    public String name;
    
    private EnemyType(String name)
    {
        this.name = name;
    }
}

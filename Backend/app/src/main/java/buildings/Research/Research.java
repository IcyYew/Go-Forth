package buildings.Research;

import jakarta.persistence.*;

@Entity(name = "researches")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Research {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer researchId;

    @Column(name = "power")
    private double power;

    @Column(name = "tier")
    private int tier;

    @Column(name = "label")
    private String researchName;

    @Column(name = "level")
    private int level;

    @Column(name = "platinum-cost")
    private double platinumCost = 1;

    @ManyToOne
    @JoinColumn(name = "research_manager_id")
    private ResearchManager researchManager;


    public Research(String researchName, int level, int tier, ResearchManager researchManager) {
        setResearchName(researchName);
        setResearchManager(researchManager);
        setTier(tier);
        setLevel(level);
        setPlatinumCost(50 + (this.level * 200) + (this.tier * 500));
        setPower(0);
    }


    public Research() {

    }

    public ResearchManager getResearchManager() {
        return researchManager;
    }

    public void setResearchManager(ResearchManager researchManager) {
        this.researchManager = researchManager;
    }

    public Integer getResearchId() {
        return researchId;
    }

    public void setResearchId(Integer researchId) {
        this.researchId = researchId;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public String getResearchName() {
        return researchName;
    }

    public void setResearchName(String researchName) {
        this.researchName = researchName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        setPower(getPower() + (this.level * 500) + (this.tier * 750));
        setPlatinumCost(getPlatinumCost() + (this.level * 200) + (this.tier * 500));
    }
    public void levelUpResearch(int level, ResearchManager researchManager) {
        this.level = level;
        setPower(getPower() + (this.level * 500) + (this.tier * 750));
        setPlatinumCost(Math.ceil(getPlatinumCost() + (this.level * 200) + (this.tier * 500) * (Math.pow(.97, researchManager.getResearch("Research Cost").getLevel()))));
    }

    public double getPlatinumCost() {
        return platinumCost;
    }

    public void setPlatinumCost(double platinumCost) {
        this.platinumCost = platinumCost;
    }


    @Override
    public String toString() {
        return "Research{" +
                "researchId=" + researchId +
                ", power=" + power +
                ", tier=" + tier +
                ", researchName='" + researchName + '\'' +
                ", level=" + level +
                ", platinumCost=" + platinumCost +
                ", researchManager=" + researchManager +
                '}';
    }
}

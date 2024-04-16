package buildings.Research;

import jakarta.persistence.*;

@Entity
public class Research {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer researchId;

    @Column(name = "power")
    private int power;

    @Column(name = "tier")
    private int tier;

    @Column(name = "label")
    private String researchName;

    @Column(name = "level")
    private int level;

    @Column(name = "platinum-cost")
    private int platinumCost = 1;


    public Research(String researchName, int level, int tier) {
        this.researchName = researchName;
        this.tier = tier;
        
    }

    public Research() {

    }

    public Integer getResearchId() {
        return researchId;
    }

    public void setResearchId(Integer researchId) {
        this.researchId = researchId;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
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
    }

    public int getPlatinumCost() {
        return platinumCost;
    }

    public void setPlatinumCost(int platinumCost) {
        this.platinumCost = platinumCost;
    }
}

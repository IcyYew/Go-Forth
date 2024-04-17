package com.example.app;

public class Research {
    private String name;
    private int tier;
    private int level;
    private float effect;

    public Research(String name, int tier, int level, float effect) {
        this.name = name;
        this.tier = tier;
        this.level = level;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getEffect() {
        return effect;
    }

    public void setEffect(float effect) {
        this.effect = effect;
    }
}

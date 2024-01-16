package de.buw.se4de;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;

class AccountData{

    private int Index_ = 0;

    public float get_weight(AccountFile ACFile){
        return Float.parseFloat(ACFile.readAccount(Index_, "weight"));
    }

    public float get_bmi(AccountFile ACFile){
        float weight = Float.parseFloat(ACFile.readAccount(Index_, "weight"));
        float height = Float.parseFloat(ACFile.readAccount(Index_, "height"));
        float bmi = weight / (float)Math.pow(height/100.0f, 2);

        return bmi;
    }

    public float get_daily_kcal(AccountFile ACFile){
        String gender = (ACFile.readAccount(Index_, "gender"));
        float daily_kcal = 0;
        float weight = Float.parseFloat(ACFile.readAccount(Index_, "weight"));
        float goal_weight = Float.parseFloat(ACFile.readAccount(Index_, "goal_weight"));
        if(Objects.equals(gender, "male"))
        {
            daily_kcal = get_weight(ACFile)*24*1*1.4f;
        }
        else
        {
            daily_kcal = get_weight(ACFile)*24*0.9f*1.4f;
        }

        if(goal_weight>weight) {
            daily_kcal += 500;
        }else if(goal_weight<weight){
            daily_kcal -= 500;
        }
        return daily_kcal;
    }

    public float get_ate_kcal(AccountFile ACFile, int new_kcal){
        return (Float.parseFloat(ACFile.readAccount(Index_, "nutritions_day"))+new_kcal);
    }

    public float get_left_kcal(AccountFile ACFile){
        return get_daily_kcal(ACFile)-get_ate_kcal(ACFile,0);
    }

    public float get_streak(AccountFile ACFile){
        return Float.parseFloat(ACFile.readAccount(Index_, "streak"));
    }

    public void update_streak(AccountFile ACFile){
        int streak = Integer.parseInt(ACFile.readAccount(Index_, "streak"));
        String new_streak = Integer.toString(streak);
        ACFile.editAccount(new_streak, Index_, 7);
    };

    public void setIndex(int Index){Index_ = Index;};

    public void update_account(AccountFile ACFile, Label BMI, Label Nutri, Label Streak){
        BMI.setText("Your BMI: " + String.format("%.0f", get_bmi(ACFile)));
        Nutri.setText("Kcal per day for you: " + String.format("%.0f", get_daily_kcal(ACFile)) +
                "\nKcal already consumed today: " + String.format("%.0f", get_ate_kcal(ACFile, 0)) +
                "\nKcal still left for today: " + String.format("%.0f", get_left_kcal(ACFile)));
        Streak.setText("Your Streak: " + get_streak(ACFile)+ " days");
    }

    public void update_edit(AccountFile ACFile, Label name, Label height, Label gender, Label weight, Label goalweight){
        name.setText("Current name: " + ACFile.readAccount(getIndex_(), "name"));
        height.setText("Current height: " + ACFile.readAccount(getIndex_(), "height"));
        gender.setText("Current gender: " + ACFile.readAccount(getIndex_(), "gender"));
        weight.setText("Current weight: " + ACFile.readAccount(getIndex_(), "weight"));
        goalweight.setText("Current goal weight: " + ACFile.readAccount(getIndex_(), "goal_weight"));
    }
    public int getIndex_() {
        return Index_;
    }
}
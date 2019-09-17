package com.xuxe.falconHeavy.commands.fun.trivia;

import com.xuxe.falconHeavy.utils.HtmlManipulator;

class TriviaInstance {
    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String all_answers;
    private String correct_answer;
    private String[] incorrect_answers;
    private int points = 0;
    private int random = (int) (Math.random() * 3);

    String getCategory() {
        return category;
    }

    String getType() {
        return type;
    }

    String getDifficulty() {
        return difficulty;
    }

    String getQuestion() {
        question = HtmlManipulator.replaceHtmlEntities(question);
        return HtmlManipulator.sanitize(question);
    }

    String getCorrect_answer() {
        return correct_answer;
    }

    String getAll_answers() {
        String[] all_answers_arr = new String[4];
        all_answers_arr[random] = correct_answer;
        int j = 0;
        for (int i = 0; i < 4; i++) {
            if (i == random) continue;
            all_answers_arr[i] = incorrect_answers[j];
            j++;
        }
        String all_answers = "";
        for (int i = 0; i < 4; i++) {
            all_answers = all_answers.concat((char) (65 + i) + "." + all_answers_arr[i] + "\n");
        }
        all_answers = HtmlManipulator.sanitize(all_answers);
        all_answers = HtmlManipulator.replaceHtmlEntities(all_answers);
        return all_answers;
    }

    int getPoints() {
        switch (difficulty) {
            case "hard":
                points = 3;
                break;
            case "medium":
                points = 2;
                break;
            case "easy":
                points = 1;
                break;
        }
        return points;
    }

    char getCorrectAnswerLetter() {
        switch (random) {
            case 0:
                return 'a';
            case 1:
                return 'b';
            case 2:
                return 'c';
            case 3:
                return 'd';
            default:
                return 'x';
        }
    }
}


package com.example.mcqassignment;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.mcqassignment.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayList<QuestionModel> questionsList;
    int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        populateList();

        changePosition(0);


        binding.nextButton.setOnClickListener(view -> {
            if ((currentPosition + 1) != questionsList.size()) {
                changePosition(currentPosition + 1);
            } else {
                Toast.makeText(this, "This is the last question", Toast.LENGTH_SHORT).show();
            }
        });

        binding.previousButton.setOnClickListener(view -> {
            if ((currentPosition - 1) >= 0) {
                changePosition(currentPosition - 1);
            } else {
                Toast.makeText(this, "This is the first question", Toast.LENGTH_SHORT).show();
            }
        });

        binding.optionsGroup.setOnCheckedChangeListener((radioGroup, i) -> {
//            Toast.makeText(MainActivity.this, "Selected: " + i, Toast.LENGTH_SHORT).show();

            int selectedAnswer = -1;

            if (i == R.id.option1) {
                selectedAnswer = 1;
            } else if (i == R.id.option2) {
                selectedAnswer = 2;
            } else if (i == R.id.option3) {
                selectedAnswer = 3;
            } else if (i == R.id.option4) {
                selectedAnswer = 4;
            }

            questionsList.get(currentPosition).setAnswer(selectedAnswer);
//            Toast.makeText(this, "Selected option: " + questionsList.get(currentPosition).getAnswer(), Toast.LENGTH_SHORT).show();
        });
    }

    private void changePosition(int i) {
        currentPosition = i;

        QuestionModel question = questionsList.get(currentPosition);

        binding.questionTv.setText(question.getQuestion());
        binding.option1.setText(question.getOption1());
        binding.option2.setText(question.getOption2());
        binding.option3.setText(question.getOption3());
        binding.option4.setText(question.getOption4());
//        binding.imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
//                question.getImage()));
        imageViewAnimatedChange(question.getImage());

        int selectedAnswer = question.getAnswer();
        switch (selectedAnswer) {
            case 1:
                binding.optionsGroup.check(R.id.option1);
                break;
            case 2:
                binding.optionsGroup.check(R.id.option2);
                break;
            case 3:
                binding.optionsGroup.check(R.id.option3);
                break;
            case 4:
                binding.optionsGroup.check(R.id.option4);
                break;
            default:
                binding.optionsGroup.clearCheck();
        }

        binding.progressBar.setMax(questionsList.size());
        binding.progressBar.setProgress(currentPosition + 1);
        binding.counterTv.setText((currentPosition + 1) + "/" + questionsList.size());

        binding.previousButton.setAlpha(1.0f);
        binding.nextButton.setAlpha(1.0f);

        if (currentPosition == 0) {
            binding.previousButton.setAlpha(0.5f);
        }

        if (currentPosition == (questionsList.size() - 1)) {
            binding.nextButton.setAlpha(0.5f);
        }
    }

    public void imageViewAnimatedChange(final int new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
        final Animation anim_in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);

        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), new_image));
                binding.imageView.startAnimation(anim_in);
            }
        });
        binding.imageView.startAnimation(anim_out);
    }

    private void populateList() {
        questionsList = new ArrayList<>();

        QuestionModel question;

        question = new QuestionModel("What is this animal called?",
                R.drawable.panda,
                "Bear", "Koala", "Panda", "Leopard", -1);
        questionsList.add(question);

        question = new QuestionModel("Name the sport.",
                R.drawable.chess,
                "Table Tennis", "Chess", "Uno", "Suduko", -1);
        questionsList.add(question);

        question = new QuestionModel("What is the name of this fruit",
                R.drawable.apple,
                "Apple", "Orange", "Pineapple", "Guava", -1);
        questionsList.add(question);
    }

    private class QuestionModel {
        private String question;
        private int image;
        private String option1;
        private String option2;
        private String option3;
        private String option4;

        private int answer;

        public QuestionModel(String question, int image, String option1, String option2, String option3, String option4, int answer) {
            this.question = question;
            this.image = image;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
            this.answer = answer;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getOption1() {
            return option1;
        }

        public void setOption1(String option1) {
            this.option1 = option1;
        }

        public String getOption2() {
            return option2;
        }

        public void setOption2(String option2) {
            this.option2 = option2;
        }

        public String getOption3() {
            return option3;
        }

        public void setOption3(String option3) {
            this.option3 = option3;
        }

        public String getOption4() {
            return option4;
        }

        public void setOption4(String option4) {
            this.option4 = option4;
        }

        public int getAnswer() {
            return answer;
        }

        public void setAnswer(int answer) {
            this.answer = answer;
        }
    }
}
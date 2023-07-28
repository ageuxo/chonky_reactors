package io.github.ageuxo.chonkyreactors.util;

public interface ProgressDataProvider {
    int getProgress();

    int getProgressMax();

    void setProgress(int progress);

    void setProgressMax(int maxProgress);
}

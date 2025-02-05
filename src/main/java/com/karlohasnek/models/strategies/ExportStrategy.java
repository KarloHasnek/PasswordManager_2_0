package com.karlohasnek.models.strategies;

import com.karlohasnek.models.PasswordEntry;

import java.util.List;

public interface ExportStrategy {

    public void export(String filePath, List<PasswordEntry> data);
}

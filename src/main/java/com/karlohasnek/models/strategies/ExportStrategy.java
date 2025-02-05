package com.karlohasnek.models.strategies;

import com.karlohasnek.models.PasswordEntry;

import java.util.List;

/**
 * Interface used for exporting data to a file.
 */
public interface ExportStrategy {

    public void export(String filePath, List<PasswordEntry> data);
}

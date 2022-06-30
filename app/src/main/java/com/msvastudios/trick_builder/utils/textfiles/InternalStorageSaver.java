package com.msvastudios.trick_builder.utils.textfiles;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class InternalStorageSaver {

        Context context;
        String fileName;
        File internalFile;


        public InternalStorageSaver(Context context, String fileName) {
            this.context = Objects.requireNonNull(context);
            this.fileName = Objects.requireNonNull(fileName);
            init();
        }

        public InternalStorageSaver(Context context, InternalFiles fileName) {
            this.context = Objects.requireNonNull(context);
            this.fileName = resolveEnum(fileName);
            init();

        }

        private String resolveEnum(InternalFiles fileName) {
            String suffix = ".txt";

            return fileName.getFileName() + suffix;
        }

        public InternalStorageSaver init() {
            internalFile = new File(context.getFilesDir(), fileName);
//        Log.d("FILES DIR", context.getFilesDir().toString());
            if (!internalFile.exists()) {
                try {
                    internalFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return this;
        }


        public boolean isEmpty() { // possible heavy operation
            return read().isEmpty();
        }

        public InternalStorageSaver append(String data) {
            FileWriter fr = null;
            try {
                fr = new FileWriter(internalFile, true);
                fr.write(data);
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public void clear() {
            try {
                if(internalFile.delete())
                    internalFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String read() {

            int length = (int) internalFile.length();

            char[] char_array = new char[length];
            StringBuilder output = new StringBuilder();
            try {
                FileReader reader = new FileReader(internalFile);

                BufferedReader bReader = new BufferedReader(reader);
                String line;
                while ((line = bReader.readLine()) != null) {
                    output.append(line);
                }
                FileInputStream fin = context.openFileInput(fileName);
                InputStreamReader isr = new InputStreamReader(fin);

                isr.read(char_array);
                fin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return output.toString();
        }

        public String[] read(String lineEnder) {
            return read().split(lineEnder);
        }


        public String getFileName() {
            return fileName;
        }


        public class CannotResolveFileEnumException extends Exception {
            public CannotResolveFileEnumException(String errorMessage) {
                super(errorMessage);
            }
        }
    }



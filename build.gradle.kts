// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath(libs.google.services)
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    // Add the dependency for the Google services Gradle plugin
    //id("com.google.gms.google-services") version "4.4.2" apply false
}
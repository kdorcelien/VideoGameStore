package org.yearup.data;


import org.yearup.models.Profile;

public interface ProfileDao
{
    Profile create(Profile profile);
    Profile getByIdLastName(int userId, String lastName);
    void update(int userId, Profile profile);
}

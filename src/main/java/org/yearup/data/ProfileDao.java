package org.yearup.data;


import org.yearup.models.Profile;

import java.util.List;

public interface ProfileDao
{
    Profile create(Profile profile);
    List<Profile> getProfile(int userId, String lastName);
    void update(int userId, Profile profile);
}

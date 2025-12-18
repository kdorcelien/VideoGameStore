package org.yearup.data;


import org.yearup.models.Profile;

import java.util.List;

public interface ProfileDao
{
    Profile create(Profile profile);
    Profile getProfile(int userId);
    void update(int userId, Profile profile);
}

package net.creeperhost.equivalentexchange.api.emcstorage;

public interface IEmcStorage
{
    double receiveEmc(double maxReceive, boolean simulate);

    double extractEmc(double maxExtract, boolean simulate);

    double getStoredEmc();

    double getMaxStored();

    boolean canExtract();

    boolean canReceive();
}

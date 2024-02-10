<?php

namespace App\Entity;

use App\Repository\DonationMRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: DonationMRepository::class)]
class DonationM
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column] 
    private ?int $donationMId = null;

    #[ORM\Column]
    private ?float $Donation_Amount = null;

    public function getdonationMId(): ?int
    {
        return $this->donationMId;
    }

    public function getDonationAmount(): ?float
    {
        return $this->Donation_Amount;
    }

    public function setDonationAmount(float $Donation_Amount): static
    {
        $this->Donation_Amount = $Donation_Amount;

        return $this;
    }
}

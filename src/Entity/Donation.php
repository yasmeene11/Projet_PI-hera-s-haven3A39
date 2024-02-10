<?php

namespace App\Entity;

use App\Repository\DonationRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: DonationRepository::class)]
class Donation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $donationId = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $Donation_Date = null;

    #[ORM\Column(length: 255)]
    private ?string $Donation_Type = null;

    #[ORM\ManyToOne(inversedBy: 'donations')]
    #[ORM\JoinColumn(nullable: false)]
    #[ORM\JoinColumn(name: 'Account_Key', referencedColumnName:'accountId')]
    private ?Account $Account_Key = null;

    public function getdonationId(): ?int
    {
        return $this->donationId;
    }

    public function getDonationDate(): ?\DateTimeInterface
    {
        return $this->Donation_Date;
    }

    public function setDonationDate(\DateTimeInterface $Donation_Date): static
    {
        $this->Donation_Date = $Donation_Date;

        return $this;
    }

    public function getDonationType(): ?string
    {
        return $this->Donation_Type;
    }

    public function setDonationType(string $Donation_Type): static
    {
        $this->Donation_Type = $Donation_Type;

        return $this;
    }

    public function getAccountKey(): ?Account
    {
        return $this->Account_Key;
    }

    public function setAccountKey(?Account $Account_Key): static
    {
        $this->Account_Key = $Account_Key;

        return $this;
    }
}

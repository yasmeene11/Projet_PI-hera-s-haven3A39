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
    private ?int $id = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $Donation_Date = null;

    #[ORM\Column(length: 255)]
    private ?string $Donation_Type = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(nullable: false)]
    private ?Account $User_Key = null;

    public function getId(): ?int
    {
        return $this->id;
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

    public function getUserKey(): ?Account
    {
        return $this->User_Key;
    }

    public function setUserKey(?Account $User_Key): static
    {
        $this->User_Key = $User_Key;

        return $this;
    }
}

<?php

namespace App\Entity;

use App\Repository\AdoptionRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: AdoptionRepository::class)]
class Adoption
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $Adoption_Date = null;

    #[ORM\Column(length: 255)]
    private ?string $Adoption_Status = null;

    #[ORM\Column]
    private ?float $Adoption_Fee = null;

    #[ORM\OneToOne(cascade: ['persist', 'remove'])]
    #[ORM\JoinColumn(nullable: false)]
    private ?Animal $Animal_Key = null;

    #[ORM\ManyToOne]
    private ?Account $User_Key = null;

    #[ORM\ManyToOne(inversedBy: 'Adoption_Key')]
    private ?CashRegister $cash_Register = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getAdoptionDate(): ?\DateTimeInterface
    {
        return $this->Adoption_Date;
    }

    public function setAdoptionDate(\DateTimeInterface $Adoption_Date): static
    {
        $this->Adoption_Date = $Adoption_Date;

        return $this;
    }

    public function getAdoptionStatus(): ?string
    {
        return $this->Adoption_Status;
    }

    public function setAdoptionStatus(string $Adoption_Status): static
    {
        $this->Adoption_Status = $Adoption_Status;

        return $this;
    }

    public function getAdoptionFee(): ?float
    {
        return $this->Adoption_Fee;
    }

    public function setAdoptionFee(float $Adoption_Fee): static
    {
        $this->Adoption_Fee = $Adoption_Fee;

        return $this;
    }

    public function getAnimalKey(): ?Animal
    {
        return $this->Animal_Key;
    }

    public function setAnimalKey(Animal $Animal_Key): static
    {
        $this->Animal_Key = $Animal_Key;

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

    public function getcashRegister(): ?CashRegister
    {
        return $this->cash_Register;
    }

    public function setCashRegister(?CashRegister $cash_Register): static
    {
        $this->cash_Register = $cash_Register;

        return $this;
    }
}

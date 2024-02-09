<?php

namespace App\Entity;

use App\Repository\BoardingRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: BoardingRepository::class)]
class Boarding
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $Start_Date = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $End_Date = null;

    #[ORM\Column]
    private ?float $Boarding_Fee = null;

    #[ORM\Column(length: 255)]
    private ?string $Boarding_Status = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(nullable: false)]
    private ?Animal $Animal_Key = null;

    #[ORM\ManyToOne]
    #[ORM\JoinColumn(nullable: false)]
    private ?Account $User_Key = null;

    #[ORM\ManyToOne(inversedBy: 'Boarding_Key')]
    private ?CashRegister $cashRegister = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getStartDate(): ?\DateTimeInterface
    {
        return $this->Start_Date;
    }

    public function setStartDate(\DateTimeInterface $Start_Date): static
    {
        $this->Start_Date = $Start_Date;

        return $this;
    }

    public function getEndDate(): ?\DateTimeInterface
    {
        return $this->End_Date;
    }

    public function setEndDate(\DateTimeInterface $End_Date): static
    {
        $this->End_Date = $End_Date;

        return $this;
    }

    public function getBoardingFee(): ?float
    {
        return $this->Boarding_Fee;
    }

    public function setBoardingFee(float $Boarding_Fee): static
    {
        $this->Boarding_Fee = $Boarding_Fee;

        return $this;
    }

    public function getBoardingStatus(): ?string
    {
        return $this->Boarding_Status;
    }

    public function setBoardingStatus(string $Boarding_Status): static
    {
        $this->Boarding_Status = $Boarding_Status;

        return $this;
    }

    public function getAnimalKey(): ?Animal
    {
        return $this->Animal_Key;
    }

    public function setAnimalKey(?Animal $Animal_Key): static
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

    public function getCashRegister(): ?CashRegister
    {
        return $this->cashRegister;
    }

    public function setCashRegister(?CashRegister $cashRegister): static
    {
        $this->cashRegister = $cashRegister;

        return $this;
    }
}

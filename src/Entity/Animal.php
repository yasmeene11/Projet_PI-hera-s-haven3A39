<?php

namespace App\Entity;

use App\Repository\AnimalRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: AnimalRepository::class)]
class Animal
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $Animal_Name = null;

    #[ORM\Column(length: 255)]
    private ?string $Animal_Breed = null;

    #[ORM\Column(length: 255)]
    private ?string $Animal_Type = null;

    #[ORM\Column(length: 255)]
    private ?string $Animal_Status = null;

    #[ORM\Column]
    private ?int $Animal_Age = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $Enrollement_Date = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getAnimalName(): ?string
    {
        return $this->Animal_Name;
    }

    public function setAnimalName(string $Animal_Name): static
    {
        $this->Animal_Name = $Animal_Name;

        return $this;
    }

    public function getAnimalBreed(): ?string
    {
        return $this->Animal_Breed;
    }

    public function setAnimalBreed(string $Animal_Breed): static
    {
        $this->Animal_Breed = $Animal_Breed;

        return $this;
    }

    public function getAnimalType(): ?string
    {
        return $this->Animal_Type;
    }

    public function setAnimalType(string $Animal_Type): static
    {
        $this->Animal_Type = $Animal_Type;

        return $this;
    }

    public function getAnimalStatus(): ?string
    {
        return $this->Animal_Status;
    }

    public function setAnimalStatus(string $Animal_Status): static
    {
        $this->Animal_Status = $Animal_Status;

        return $this;
    }

    public function getAnimalAge(): ?int
    {
        return $this->Animal_Age;
    }

    public function setAnimalAge(int $Animal_Age): static
    {
        $this->Animal_Age = $Animal_Age;

        return $this;
    }

    public function getEnrollementDate(): ?\DateTimeInterface
    {
        return $this->Enrollement_Date;
    }

    public function setEnrollementDate(\DateTimeInterface $Enrollement_Date): static
    {
        $this->Enrollement_Date = $Enrollement_Date;

        return $this;
    }
}

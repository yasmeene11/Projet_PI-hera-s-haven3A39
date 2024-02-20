<?php

namespace App\Entity;

use App\Repository\AnimalRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\Validator\Constraints as Assert;


#[ORM\Entity(repositoryClass: AnimalRepository::class)]
class Animal
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name: 'animalId')]
    private ?int $animalId = null;
     
     /**
 * @Assert\NotBlank(message="Animal Name is required.")
 * 
 */  
    #[ORM\Column(length: 255)]
    private ?string $Animal_Name = null;

     /**
 * @Assert\NotBlank(message="Animal Breed is required.")
 * 
 */
    #[ORM\Column(length: 255)]
    private ?string $Animal_Breed = null;

    /**
 * @Assert\NotBlank(message="Animal status is required.")
 * 
 */
    #[ORM\Column(length: 255)]
    private ?string $Animal_Status = null;

    /**
 * @Assert\NotBlank(message="Animal Type is required.")
 * 
 */
    #[ORM\Column(length: 255)]
    private ?string $Animal_Type = null;

    /**
 * @ORM\Column(type="integer")
 * @Assert\NotBlank(message="Age is required.")
 * @Assert\Range(
 *      min = 0,
 *      max = 15,
 *      notInRangeMessage = "Age must be between {{ min }} and {{ max }}",
 * )
 */
    #[ORM\Column]
    private ?int $Age = null;

     /**
     * @Assert\NotBlank(message="Date is required.")
     *  @Assert\GreaterThanOrEqual("today", message="The date cannot be in the past.")
     */
    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $Enrollement_Date = null;

     /**
 * @Assert\NotBlank(message="Animal Image is required.")
 * 
 */
    #[ORM\Column(length: 255)]
    private ?string $Animal_Image = null;
    
     /**
 * @Assert\NotBlank(message="Animal Description is required.")
 * 
 */
    #[ORM\Column(length: 1000)]
    private ?string $Animal_Description = null;

    #[ORM\OneToMany(mappedBy: 'Animal_Key', targetEntity: Appointment::class)]
    private Collection $appointments;

    #[ORM\OneToMany(mappedBy: 'Animal_Key', targetEntity: Adoption::class)]
    private Collection $adoptions;

    #[ORM\OneToMany(mappedBy: 'Animal_Key', targetEntity: Boarding::class)]
    private Collection $boardings;

    public function __construct()
    {
        $this->appointments = new ArrayCollection();
        $this->adoptions = new ArrayCollection();
        $this->Boardings = new ArrayCollection();
    }
   
    public function __toString(): string
    {
        return $this->Animal_Image;
    }

    public function getanimalId(): ?int
    {
        return $this->animalId;
    }

    public function getAnimalName(): ?string
    {
        return $this->Animal_Name;
    }

    public function setAnimalName(?string $Animal_Name): static
    {
        $this->Animal_Name = $Animal_Name;

        return $this;
    }

    public function getAnimalBreed(): ?string
    {
        return $this->Animal_Breed;
    }

    public function setAnimalBreed(?string $Animal_Breed): static
    {
        $this->Animal_Breed = $Animal_Breed;

        return $this;
    }

    public function getAnimalStatus(): ?string
    {
        return $this->Animal_Status;
    }

    public function setAnimalStatus(?string $Animal_Status): static
    {
        $this->Animal_Status = $Animal_Status;

        return $this;
    }

    public function getAnimalType(): ?string
    {
        return $this->Animal_Type;
    }

    public function setAnimalType(?string $Animal_Type): static
    {
        $this->Animal_Type = $Animal_Type;

        return $this;
    }

    public function getAge(): ?int
    {
        return $this->Age;
    }

    public function setAge(?int $Age): static
    {
        $this->Age = $Age;

        return $this;
    }

    public function getEnrollementDate(): ?\DateTimeInterface
    {
        return $this->Enrollement_Date;
    }
    
    public function setEnrollementDate(?\DateTimeInterface $Enrollement_Date): static
    {
        $this->Enrollement_Date = $Enrollement_Date;
    
        return $this;
    }
    

    public function getAnimalImage(): ?string
    {
        return $this->Animal_Image;
    }

    public function setAnimalImage(?string $Animal_Image): static
    {
        $this->Animal_Image = $Animal_Image;

        return $this;
    }

    
    
    /**
     * @return Collection<int, Appointment>
     */
    public function getAppointments(): Collection
    {
        return $this->appointments;
    }

    public function addAppointment(Appointment $appointment): static
    {
        if (!$this->appointments->contains($appointment)) {
            $this->appointments->add($appointment);
            $appointment->setAnimalKey($this);
        }

        return $this;
    }

    public function removeAppointment(Appointment $appointment): static
    {
        if ($this->appointments->removeElement($appointment)) {
            // set the owning side to null (unless already changed)
            if ($appointment->getAnimalKey() === $this) {
                $appointment->setAnimalKey(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Adoption>
     */
    public function getAdoptions(): Collection
    {
        return $this->adoptions;
    }

    public function addAdoption(Adoption $adoption): static
    {
        if (!$this->adoptions->contains($adoption)) {
            $this->adoptions->add($adoption);
            $adoption->setAnimalKey($this);
        }

        return $this;
    }

    public function removeAdoption(Adoption $adoption): static
    {
        if ($this->adoptions->removeElement($adoption)) {
           
            if ($adoption->getAnimalKey() === $this) {
                $adoption->setAnimalKey(null);
            }
        }

        return $this;
    }

    public function getAnimalDescription(): ?string
    {
        return $this->Animal_Description;
    }

    public function setAnimalDescription(string $Animal_Description): static
    {
        $this->Animal_Description = $Animal_Description;

        return $this;
    }
     /**
     * @return Collection<int, Boarding>
     */
    public function getBoardings(): Collection
    {
        return $this->boardings;
    }

    public function addBoarding(Boarding $boarding): static
    {
        if (!$this->boardings->contains($boarding)) {
            $this->boardings->add($boarding);
            $boarding->setAnimalKey($this);
        }

        return $this;
    }

    public function removeBoarding(Boarding $boarding): static
    {
        if ($this->boardings->removeElement($boarding)) {
            // set the owning side to null (unless already changed)
            if ($boarding->getAnimalKey() === $this) {
                $boarding->setAnimalKey(null);
            }
        }

        return $this;
    }
}

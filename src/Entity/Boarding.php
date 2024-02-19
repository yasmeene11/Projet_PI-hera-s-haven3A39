<?php

namespace App\Entity;

use App\Repository\BoardingRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Symfony\Component\Validator\Context\ExecutionContextInterface;
use Symfony\Component\Validator\Validator\ValidatorInterface;

use DateTime;

#[ORM\Entity(repositoryClass: BoardingRepository::class)]
class Boarding
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'boardingId')]
    private ?int $boardingId = null;

      /**
     * @Assert\NotBlank(message="Date is required.")
     *  @Assert\GreaterThanOrEqual("today", message="The date cannot be in the past.")
     */
    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $Start_Date = null;

      /**
     * @Assert\NotBlank(message="Date is required.")
     * @Assert\GreaterThanOrEqual("today", message="The date cannot be in the past.")
     */
    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $End_Date = null;

    /**
     * @Assert\Callback
     */
    public function validateDateRange(ExecutionContextInterface $context, $payload)
    {
        if ($this->Start_Date > $this->End_Date) {
            $context->buildViolation('Start date cannot be after the end date.')
                ->atPath('Start_Date')
                ->addViolation();
        }
    }
    
    /**
 * @Assert\NotBlank(message="Boarding status is required.")
 * 
 */
    #[ORM\Column(length: 255)]
    private ?string $Boarding_Status = null;

    /**
     * @ORM\Column(type="float")
     * @Assert\NotBlank(message="Boarding fee is required.")
     * @Assert\Positive(message="Boarding fee must be a positive number.")
     */
    #[ORM\Column]
    private ?float $Boarding_Fee = null;

    
   /**
 * @Assert\NotNull(message="Animal ID is required.")
 */
    #[ORM\ManyToOne(inversedBy: 'boardings')]
    #[ORM\JoinColumn(nullable: false, name: 'Animal_Key', referencedColumnName: 'animalId')]
    private ?Animal $Animal_Key = null;

    
   /**
 * @Assert\NotNull(message="Account ID is required.")
 */
    #[ORM\ManyToOne(inversedBy: 'boardings')]
    #[ORM\JoinColumn(nullable: false, name: 'Account_Key', referencedColumnName: 'accountId')]
    private ?Account $Account_Key = null;

    public function getboardingId(): ?int
    {
        return $this->boardingId;
    }

    public function getStartDate(): ?\DateTimeInterface
    {
        return $this->Start_Date;
    }

    public function setStartDate(?\DateTimeInterface $Start_Date): static
    {
        $this->Start_Date = $Start_Date;

        return $this;
    }

    public function getEndDate(): ?\DateTimeInterface
    {
        return $this->End_Date;
    }

    public function setEndDate(?\DateTimeInterface $End_Date): static
    {
        $this->End_Date = $End_Date;

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

    public function getBoardingFee(): ?float
    {
        return $this->Boarding_Fee;
    }

    public function setBoardingFee(float $Boarding_Fee): static
    {
        $this->Boarding_Fee = $Boarding_Fee;

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
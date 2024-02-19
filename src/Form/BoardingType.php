<?php
namespace App\Form;

use App\Entity\Boarding;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use App\Repository\AnimalRepository;
use App\Entity\Account;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\FormInterface;

class BoardingType extends AbstractType
{
    private $animalRepository;

    public function __construct(AnimalRepository $animalRepository)
    {
        $this->animalRepository = $animalRepository;
    }

    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
      
        $builder->add('Start_Date', DateType::class, [
            'widget' => 'single_text',
            
        ])
            ->add('End_Date', DateType::class, [
            'widget' => 'single_text',
            
            ]);
          

            if ($options['is_admin']) {
                $builder->add('Boarding_Status', ChoiceType::class, [
                    'choices' => [
                        'Cancelled' => 'Cancelled',
                        'Pending' => 'Pending',
                        'Completed' => 'Completed',
                    ],
                    'placeholder' => 'Select Status',
                    'required' => true,
                ])
                ->add('Boarding_Fee')
                ->add('Animal_Key', EntityType::class, [
                    'class' => 'App\Entity\Animal',
                    'choices' => $this->animalRepository->findByStatus('Here for Boarding'),
                    'choice_label' => 'Animal_Name',
                    'placeholder' => 'Select Animal',
                    'attr' => [
                        'class' => 'form-control',
                    ],
                ])
                ->add('Account_Key', EntityType::class, [
                    'class' => Account::class, 
                    'choice_label' => 'name',
                    'placeholder' => 'Select Account',
                    'required' => true,
                ]);
            } else {
                // For the front office, set fields as not mapped or use HiddenType
                $builder
                    ->add('Boarding_Status', HiddenType::class, [
                        'mapped' => false,
                    ])
                    ->add('Boarding_Fee', HiddenType::class, [
                        'mapped' => false,
                    ])
                    ->add('Animal_Key', HiddenType::class, [
                        'mapped' => false,
                    ])
                    ->add('Account_Key', HiddenType::class, [
                        'mapped' => false,
                    ]);
            }
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Boarding::class,
            'is_admin' => false,
            
            'validation_groups' => function (FormInterface $form) {
                $groups = ['Default'];

                if ($form->getConfig()->getOption('is_admin')) {
                    $groups[] = 'admin';
                }

                return $groups;
            },
        ]);

       
        $resolver->setRequired('animalRepository');
    }
}